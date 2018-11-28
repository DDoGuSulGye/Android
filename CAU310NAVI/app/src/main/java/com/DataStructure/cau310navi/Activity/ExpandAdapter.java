package com.DataStructure.cau310navi.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.DataStructure.cau310navi.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by leesd on 2018-11-25.
 */

public class ExpandAdapter extends BaseExpandableListAdapter {
    private static final int PARENT_ROW = R.layout.expand_parent;
    private static final int CHILD_ROW = R.layout.expand_child;
    private Context context = null;
    private ArrayList<ParentData> data;
    private LayoutInflater layoutInflater = null;

    public ExpandAdapter(Context context, ArrayList<ParentData> data){
        this.data = data;
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).child.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(PARENT_ROW, parent, false);
        }

        TextView parentName = (TextView)convertView.findViewById(R.id.parentName);
        parentName.setText(((ParentData)getGroup(groupPosition)).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(CHILD_ROW, parent, false);
        }

        TextView childName = (TextView)convertView.findViewById(R.id.childName);
        childName.setText(getChild(groupPosition, childPosition).toString());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
