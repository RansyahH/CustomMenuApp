package com.example.myapplication.callbacks;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import com.example.myapplication.R;

public abstract class MainActionModeCallback implements ActionMode.Callback {
    private ActionMode action;
    private MenuItem countItem;
    private MenuItem shareItem;

    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        actionMode.getMenuInflater().inflate(R.menu.main_action_mode, menu);
        this.action = actionMode;
        this.countItem = menu.findItem(R.id.action_checked_count);
        this.shareItem = menu.findItem(R.id.action_share_note);
        return true;
    }

    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    public void onDestroyActionMode(ActionMode actionMode) {
    }

    public void setCount(String chackedCount) {
        MenuItem menuItem = this.countItem;
        if (menuItem != null) {
            menuItem.setTitle(chackedCount);
        }
    }

    public void changeShareItemVisible(boolean b) {
        this.shareItem.setVisible(b);
    }

    public ActionMode getAction() {
        return this.action;
    }
}
