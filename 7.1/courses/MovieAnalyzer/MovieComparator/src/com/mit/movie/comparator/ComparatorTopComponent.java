/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.movie.comparator;

import com.mit.movie.domain.CentralLookup;
import com.mit.movie.domain.Match;
import com.mit.movie.domain.Movie;
import java.awt.BorderLayout;
import java.util.Collection;
import java.util.List;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//com.mit.movie.comparator//Comparator//EN",
autostore = false)
@TopComponent.Description(preferredID = "ComparatorTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE",
persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "com.mit.movie.comparator.ComparatorTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_ComparatorAction",
preferredID = "ComparatorTopComponent")
@Messages({
    "CTL_ComparatorAction=Analyzer",
    "CTL_ComparatorTopComponent=Analyzer",
})
public final class ComparatorTopComponent extends TopComponent implements LookupListener, ExplorerManager.Provider {

    private Movie movie1;
    private Movie movie2;
    private String message;
    private ExplorerManager em = new ExplorerManager();
//    private InstanceContent ic = new InstanceContent();

    public ComparatorTopComponent() {
	initComponents();
	setName(Bundle.CTL_ComparatorTopComponent());
	setLayout(new BorderLayout());
	BeanTreeView btv = new BeanTreeView();
	btv.setRootVisible(false);
	add(btv, BorderLayout.CENTER);
	em.setRootContext(new AbstractNode(Children.LEAF));
//	associateLookup(new AbstractLookup(ic));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    Result<Movie> result = null;

    @Override
    public void componentOpened() {
	result = Utilities.actionsGlobalContext().lookupResult(Movie.class);
	result.addLookupListener(this);
	resultChanged(new LookupEvent(result));
    }

    @Override
    public void componentClosed() {
	result.removeLookupListener(this);
    }

    void writeProperties(java.util.Properties p) {
	// better to version settings since initial version as advocated at
	// http://wiki.apidesign.org/wiki/PropertyFiles
	p.setProperty("version", "1.0");
	// TODO store your settings
    }

    void readProperties(java.util.Properties p) {
	String version = p.getProperty("version");
	// TODO read your settings according to their version
    }

    void setUniqueList(Collection<String> collection) {
	if (collection.isEmpty()) {
	    CentralLookup.getDefault().add(new Match(
		    "The movies " + message + " have nothing in common.",
		    "com/mit/movie/comparator/no_good.gif"));
	} else {
	    for (String person : collection) {
		CentralLookup.getDefault().add(new Match(
			person.toUpperCase() + " was in both of " + message,
			"com/mit/movie/comparator/fact_good.png"));
	    }
	}
	Children kids = Children.create(new ProcessedChildFactory(), true);
	em.setRootContext(new AbstractNode(kids));
    }

    @Override
    public void resultChanged(LookupEvent ev) {
	for (Movie movie : result.allInstances()) {
	    movie1 = movie;
	    movie2 = result.allInstances().iterator().next();
	}
	message = "\"" + movie1.getTitle() + "\"" + " and " + "\"" + movie2.getTitle() + "\"";
    }

    @Override
    public ExplorerManager getExplorerManager() {
	return em;
    }

    private class ProcessedChildFactory extends ChildFactory.Detachable<Match> implements LookupListener {

	Result<Match> result = null;

	@Override
	protected void addNotify() {
	    result = CentralLookup.getDefault().lookupResult(Match.class);
	    result.addLookupListener(this);
	}

	@Override
	protected void removeNotify() {
	    result.removeLookupListener(this);
	}

	@Override
	protected boolean createKeys(List toPopulate) {
	    toPopulate.addAll(result.allInstances());
	    return true;
	}

	@Override
	protected Node createNodeForKey(Match key) {
	    AbstractNode node = new AbstractNode(Children.LEAF);
	    node.setDisplayName(key.getDisplayText());
	    node.setIconBaseWithExtension(key.getIcon());
	    return node;
	}

	@Override
	public void resultChanged(LookupEvent ev) {
	    refresh(true);
	}

    }

}
