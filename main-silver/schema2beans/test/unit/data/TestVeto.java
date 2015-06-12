/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */
/*
 *	TestVeto - test the vetoable events
 *
 */

import java.io.*;
import java.util.*;
import org.w3c.dom.*;

import java.beans.*;

import org.netbeans.modules.schema2beans.*;
import book.*;


public class TestVeto extends BaseTest
{
    public static void main(String[] argv) {
        BaseTest o = new TestVeto();
        if (argv.length > 0)
            o.setDocumentDir(argv[0]);
        try {
            o.run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
    
	public class MyListener implements VetoableChangeListener 
	{
		GraphManager	gm;
		String			listenerName;
		String			title;
		Object			oldValue;
		Object			newValue;
		String			propertyName;
		Object			source;
		int				index;

		boolean			remove;
		
		//	Used to check that the event is triggered once the changed are done
		Chapter			tracePara;
		
		boolean			raise = false;;
		boolean			raised = false;
		boolean			printStringArray = false;

		public MyListener(String title, BaseBean bean)
		{
			this.listenerName = bean.name();
			gm = bean.graphManager();
			this.remove = false;
			this.title = title;
			out("new listener for " + this.title + " / " + this.listenerName);
		}

		public void printStringValues(String[] ss, String str)
		{
			if (ss == null)
				out(str + " is null");
			else
			{
				if (ss.length == 0)
					out(str + ".length = 0");
				else
					for(int i=0; i<ss.length; i++)
						out(str + "[" + i + "]=" + ss[i]);
			}
		}

		public void reset()
		{
			this.oldValue = null;
			this.newValue = null;
			this.propertyName = null;
			this.source = null;
			this.index = -1;
			this.raise = false;
			this.raised = false;
			this.printStringArray = false;
		}

		public void printStringArray()
		{
			this.printStringArray = true;
		}

		public void traceParagraphs(Chapter c)
		{
			this.tracePara = c;
		}

		public void veto()
		{
			this.raise = true;
		}
		
        public void vetoableChange(PropertyChangeEvent e) 
			throws PropertyVetoException
		{
			if (this.raised)
				out(this.title+ " received an undo event:");
			else
				out(this.title + " received veto event:");

			this.oldValue = e.getOldValue();
			this.newValue = e.getNewValue();
			this.propertyName = e.getPropertyName();
			this.source = e.getSource();
			String n = this.propertyName;
			this.index = gm.getPropertyIndex(n);

			out("<Lnr:" + this.listenerName + " Evt:" + n + 
				" Src:" + this.source.getClass().getName() + ">");
			if (remove)
			{
				out("<" + gm.getPropertyName(n) + "[" + this.index + 
					"]" + " - Parent: " + gm.getPropertyParentName(n) + ">");
			}
			else
			{
				out("<" + gm.getPropertyName(n) + "[" + this.index + 
					"]" + " - Parent: " + gm.getPropertyParentName(n) +
					"/" + gm.getPropertyParent(n).getClass().getName() + ">");
			}

			if (this.tracePara != null)
			{
				String[] p = this.tracePara.getParagraph();
				for (int i=0; i<p.length; i++)
					out("From event listener: " + p[i]);
			}

			if (this.printStringArray)
			{
				this.printStringValues((String[])this.oldValue, "oldValues");
				this.printStringValues((String[])this.newValue, "newValues");
			}

			if (this.raise && !this.raised)
			{
				out("Listener: raising PropertyVetoException");
				this.raised = true;
				throw new PropertyVetoException("value rejected", e);
			}
			//out("received " + this.oldValue + "/" + this.newValue + "/" +
			//	this.propertyName);
		}

		public void removeMode()
		{
			this.remove = true;
		}
		
		public Object oldValue()
		{
			return this.oldValue;
		}

		public String stringOldValue()
		{
			if (this.oldValue == null)
				return "<null>";
			else
				return this.oldValue.toString();
		}

		public Object newValue()
		{
			return this.newValue;
		}

		public String stringNewValue()
		{
			if (this.newValue == null)
				return "<null>";
			else
				return this.newValue.toString();
		}
		
		public String name()
		{
			return this.propertyName;
		}

		public String toString()
		{
			return this.name() + " raised from source " +
				this.source.getClass().getName();
		}
	}
	
	public void run()
		throws Exception
	{
		Book book;

		this.readDocument();
		
		out("creating the bean graph");
		book = Book.createGraph(this.doc);
		GraphManager gm = book.graphManager();

		out("bean graph created");
		//out(book.toString());

		/*
		 *	Book
		 *	  Index[1,n]
		 *	         Word - String
		 *	         Ref[1,n]
		 *	                Page - String
		 *	                Line - String
		 *	  Chapter[1,n]
		 *	         Comment? - String
		 *	         Paragraph[0,n] - String
		 *	  Summary? - String
		 *	  Author[1,n] - String
		 *	  Good - Boolean
		 *	  Available - Boolean
		 */

		//
		//	Set a listener on the root
		//
		MyListener l = new MyListener("Book listener", book);		
		book.addVetoableChangeListener(l);
		//GraphManager.debug(true);
		setTest("simple change event on the root - no veto");
		l.reset();
		String s = "This book is about how to veto changes";
		//	Change a property on the root - this should raises an event
		book.setSummary(s);
		//	Check the received event
		check(l.oldValue() == null, "(old value)");
		check(l.newValue().equals(s), "(new value)");
		check(book.getSummary().equals(s), "(new value)");

		boolean gotException = false;
		setTest("simple change event on the root - veto");
		l.reset();
		l.veto();
		String s2 = "this is the new value";
		s = book.getSummary();
		try {
			book.setSummary(s2);
		} catch(PropertyVetoException ve) {
			check(book.getSummary().equals(s), "(got exception & same value)");
			gotException = true;
	    }

		if (!gotException)
			check(false, " didn't get the veto exception (1)!");

		setTest("Try to listen for a non vetoable property");
		gotException = false;
		try {
			book.addVetoableChangeListener("Reviews", l);
		} catch(Exception e) {
			check(true, "got exception:\n" + e.getMessage());
			gotException = true;
		}

		if (!gotException)
			check(false, " didn't get the runtime exception (2)!");

		setTest("Indexed final property");
		l.reset();
		String [] ss = {"Author1", "Author2", "Author3"};
		l.printStringArray();
		book.setAuthor(ss);

		l.reset();
		l.veto();
		l.printStringArray();
		String [] ss2 = {"Author1_new", "Author2_new"};
		try {
			book.setAuthor(ss2);
		} catch(PropertyVetoException ve) {
			check(true, "(got exception)");
			gotException = true;
	    }

		if (!gotException)
			check(false, " didn't get the veto exception (3)!");

		l.printStringValues(book.getAuthor(), "getAuthor()");
		
		setTest("Set a second listener to get two events");
		MyListener l2 = new MyListener("Author listener", book);		
		l.reset();
		l2.reset();
		gotException = false;
		try {
			book.addVetoableChangeListener("author", l2);
		} catch(Exception e) {
			check(false, "got exception:\n" + e.getMessage());
			gotException = true;
		}

		String [] ss3 = {"re-Author1_new", "re-Author2_new"};
		book.setAuthor(ss3);
		l.printStringValues(book.getAuthor(), "getAuthor()");

		l.reset();
		l2.reset();
		l.veto();
		String [] ss4 = {"only_one_author"};
		try {
			book.setAuthor(ss4);
		} catch(PropertyVetoException ve) {
			check(true, "(got exception)");
			gotException = true;
	    }

		if (!gotException)
			check(false, " didn't get the veto exception (4)!");

 	}

}



