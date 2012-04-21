package com.monstersoftwarellc.timeease;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.monstersoftwarellc.timeease.view.ApplicationSettingsView;
import com.monstersoftwarellc.timeease.view.EntriesView;
import com.monstersoftwarellc.timeease.view.ReportABugEnhancementView;


public class Perspective implements IPerspectiveFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);

		//layout.addStandaloneView(EntriesView.ID, false, IPageLayout.LEFT, .95f,
		//		layout.getEditorArea());
		// we just add new view placeholders for all possible views we will
		// call. The FolderLayout will allow the other views to populate the
		// same area, just like having many classes open in eclipse.
		IFolderLayout folder = layout.createFolder("otherViews", IPageLayout.LEFT, 0.95f, layout.getEditorArea());
		folder.addPlaceholder(EntriesView.ID + ":*");
		folder.addPlaceholder(ReportABugEnhancementView.ID + ":*");
		folder.addPlaceholder(ApplicationSettingsView.ID + ":*");
		// if we wanted to add them by default
		//folder.addView(EntriesView.ID);
		//folder.addView(ReportABugEnhancementView.ID);
		//folder.addView(ApplicationSettingsView.ID);
	}
}
