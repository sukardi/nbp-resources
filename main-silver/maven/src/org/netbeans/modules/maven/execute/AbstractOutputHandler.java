/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.maven.execute;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.modules.maven.api.execute.RunConfig;
import org.netbeans.modules.maven.api.output.ContextOutputProcessorFactory;
import org.netbeans.modules.maven.api.output.NotifyFinishOutputProcessor;
import org.netbeans.modules.maven.api.output.OutputProcessor;
import org.netbeans.modules.maven.api.output.OutputProcessorFactory;
import org.netbeans.modules.maven.api.output.OutputVisitor;
import org.netbeans.api.project.Project;
import org.netbeans.modules.project.indexingbridge.IndexingBridge;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.RequestProcessor;
import org.openide.windows.IOColorLines;
import org.openide.windows.IOColorPrint;
import org.openide.windows.IOColors;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;

/**
 *
 * @author mkleint
 */
public abstract class AbstractOutputHandler {

    public enum Level {DEBUG, INFO, WARNING, ERROR, FATAL}

    protected static final String PRJ_EXECUTE = "project-execute"; //NOI18N
    protected static final String SESSION_EXECUTE = "session-execute"; //NOI18N
    
    protected HashMap<String, Set<OutputProcessor>> processors;
    protected Set<OutputProcessor> currentProcessors;
    protected Set<NotifyFinishOutputProcessor> toFinishProcessors;
    protected OutputVisitor visitor;
    private IndexingBridge.Lock protectedMode; // #211005
    private final Object protectedModeLock = new Object();
    private RequestProcessor.Task sleepTask;
    private static final int SLEEP_DELAY = 5000;

    protected AbstractOutputHandler(final ProgressHandle hand, OutputVisitor visitor) {
        processors = new HashMap<String, Set<OutputProcessor>>();
        currentProcessors = new HashSet<OutputProcessor>();
        this.visitor = visitor;
        toFinishProcessors = new HashSet<NotifyFinishOutputProcessor>();
        sleepTask = new RequestProcessor(AbstractOutputHandler.class).create(new Runnable() {
            public @Override void run() {
                hand.suspend("");
                exitProtectedMode();
            }
        });
        enterProtectedMode(true);
    }

    private void enterProtectedMode(boolean wait) {
        synchronized (protectedModeLock) {
            if (protectedMode == null) {
                protectedMode = IndexingBridge.getDefault().protectedMode(wait);
            }
        }
    }
    private void exitProtectedMode() {
        synchronized (protectedModeLock) {
            if (protectedMode != null) {
                protectedMode.release();
                protectedMode = null;
            }
        }
    }

    protected abstract InputOutput getIO();

    protected void checkSleepiness() {
        RequestProcessor.Task task = sleepTask;
        if (task != null) {
            task.schedule(SLEEP_DELAY);
            enterProtectedMode(false);
        }
    }


    protected final void quitSleepiness() {
        RequestProcessor.Task task = sleepTask;
        if (task != null) {
            task.cancel();
            sleepTask = null;
            exitProtectedMode();
        }
    }
    
//TODO - replacement?    abstract MavenEmbedderLogger getLogger();

    protected final String getEventId(String eventName, String target) {
        if (PRJ_EXECUTE.equals(eventName) || SESSION_EXECUTE.equals(eventName)) {
            return eventName;
        }
        
        return eventName + "#" + target; //NOI18N
    }
    
    protected final void initProcessorList(Project proj, RunConfig config) {
        // get the registered processors.
        Lookup.Result<OutputProcessorFactory> result  = Lookup.getDefault().lookupResult(OutputProcessorFactory.class);
        Iterator<? extends OutputProcessorFactory> it = result.allInstances().iterator();
        while (it.hasNext()) {
            OutputProcessorFactory factory = it.next();
            Set<? extends OutputProcessor> procs = factory.createProcessorsSet(proj);
            if (factory instanceof ContextOutputProcessorFactory) {
                Set<OutputProcessor> _procs = new HashSet<OutputProcessor>(procs);
                _procs.addAll(((ContextOutputProcessorFactory)factory).createProcessorsSet(proj, config));
                procs = _procs;
            }
            for (OutputProcessor proc : procs) {
                String[] regs = proc.getRegisteredOutputSequences();
                for (int i = 0; i < regs.length; i++) {
                    String str = regs[i];
                    Set<OutputProcessor> set = processors.get(str);
                    if (set == null) {
                        set = new HashSet<OutputProcessor>();
                        processors.put(str, set);
                    }
                    set.add(proc);
                }
            }
        }
    }
    
    protected final void processStart(String id, OutputWriter writer) {
        checkSleepiness();
        Set<OutputProcessor> set = processors.get(id);
        if (set != null) {
            currentProcessors.addAll(set);
        }
        visitor.resetVisitor();
        for (OutputProcessor proc : currentProcessors) {
            proc.sequenceStart(id, visitor);
        }
        if (visitor.getLine() != null) {
            if (visitor.getOutputListener() != null) {
                try {
                    writer.println(visitor.getLine(), visitor.getOutputListener(), visitor.isImportant());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                if (visitor.getColor(getIO()) != null && IOColorLines.isSupported(getIO())) {
                    try {
                        IOColorLines.println(getIO(), visitor.getLine(), visitor.getColor(getIO()));
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                } else {
                    writer.println(visitor.getLine());
                }
            }
        }
    }
    
    protected final void processEnd(String id, OutputWriter writer) {
        checkSleepiness();
        visitor.resetVisitor();
        Iterator<OutputProcessor> it = currentProcessors.iterator();
        while (it.hasNext()) {
            OutputProcessor proc = it.next();
            proc.sequenceEnd(id, visitor);
            if (proc instanceof NotifyFinishOutputProcessor) {
                toFinishProcessors.add((NotifyFinishOutputProcessor)proc);
            }
        }
        if (visitor.getLine() != null) {
            if (visitor.getOutputListener() != null) {
                try {
                    writer.println(visitor.getLine(), visitor.getOutputListener(), visitor.isImportant());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                writer.println(visitor.getLine());
            }
        }
        Set set = processors.get(id);
        if (set != null) {
            //TODO a bulletproof way would be to keep a list of currently started
            // sections and compare to the list of getRegisteredOutputSequences fo each of the
            // processors in set..
            currentProcessors.removeAll(set);
        }
    }
    
    protected final void processFail(String id, OutputWriter writer) {
        checkSleepiness();
        visitor.resetVisitor();
        for (OutputProcessor proc : currentProcessors) {
            if (proc instanceof NotifyFinishOutputProcessor) {
                toFinishProcessors.add((NotifyFinishOutputProcessor)proc);
            }
            proc.sequenceFail(id, visitor);
        }
        if (visitor.getLine() != null) {
            if (visitor.getOutputListener() != null) {
                try {
                    writer.println(visitor.getLine(), visitor.getOutputListener(), visitor.isImportant());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                writer.println(visitor.getLine());
            }
        }
        Set<OutputProcessor> set = processors.get(id);
        if (set != null) {
            Set<OutputProcessor> retain = new HashSet<OutputProcessor>();
            retain.addAll(set);
            retain.retainAll(currentProcessors);
            Set<OutputProcessor> remove = new HashSet<OutputProcessor>();
            remove.addAll(set);
            remove.removeAll(retain);
            currentProcessors.removeAll(remove);
        }
        
    }
    
    protected final void buildFinished() {
        quitSleepiness();
        for (NotifyFinishOutputProcessor proc : toFinishProcessors) {
            proc.buildFinished();
        }
    }
    
    protected final void processMultiLine(String input, OutputWriter writer, Level level) {
        if (input == null) {
            return;
        }
        //MEVENIDE-637
        for (String s : splitMultiLine(input)) {
            processLine(s, writer, level);
        }
    }
    
    protected final void processLine(String input, OutputWriter writer, Level level) {
        checkSleepiness();
        
        visitor.resetVisitor();
        for (OutputProcessor proc : currentProcessors) {
            proc.processLine(input, visitor);
        }
        if (!visitor.isLineSkipped()) {
            String line = visitor.getLine() == null ? input : visitor.getLine();
            if (visitor.getColor(getIO()) == null && visitor.getOutputListener() == null) {
                switch (level) {
                case DEBUG:
                    visitor.setOutputType(IOColors.OutputType.LOG_DEBUG);
                    break;
                case WARNING:
                    visitor.setOutputType(IOColors.OutputType.LOG_WARNING);
                    break;
                case ERROR:
                case FATAL:
                    visitor.setOutputType(IOColors.OutputType.LOG_FAILURE);
                    break;
                }
            }
            try {
                if (visitor.getOutputListener() != null) {
                    if (visitor.getColor(getIO()) != null && IOColorPrint.isSupported(getIO())) {
                        IOColorPrint.print(getIO(), line + "\n", visitor.getOutputListener(), visitor.isImportant(), visitor.getColor(getIO()));
                    } else {
                        writer.println(line, visitor.getOutputListener(), visitor.isImportant());
                    }
                } else {
                    if (level.compareTo(Level.ERROR) >= 0 && IOColorPrint.isSupported(getIO())) {
                        IOColorPrint.print(getIO(), line + "\n", null, true, visitor.getColor(getIO()));
                    } else if (visitor.getColor(getIO()) != null && IOColorLines.isSupported(getIO())) {
                        IOColorLines.println(getIO(), line, visitor.getColor(getIO()));
                    } else {
                        writer.println(line);
                    }
                }
            } catch (IOException x) {
                x.printStackTrace();
                writer.println(line); // fallback
            }
        }
    }
    
    //MEVENIDE-637   
    public static List<String> splitMultiLine(String input) {
        List<String> list = new ArrayList<String>();
        String[] strs = input.split("\\r|\\n"); //NOI18N
        for (int i = 0; i < strs.length; i++) {
            if(strs[i].length()>0){
              list.add(strs[i]);
            }
        }
        return list;
    }   
}
