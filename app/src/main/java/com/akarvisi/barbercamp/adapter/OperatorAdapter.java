package com.akarvisi.barbercamp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.akarvisi.barbercamp.R;
import com.akarvisi.barbercamp.domain.Employee;
import com.akarvisi.barbercamp.domain.Operator;

import org.w3c.dom.Text;


/**
 * Created by hizry on 3/30/2018.
 */

public class OperatorAdapter extends BaseAdapter {
    private  Context _context;
    private Employee[] _employees;
    private  final  ActionCallback _callback;

    public interface ActionCallback {
        void onSelect(Employee employee);
    }


    public  OperatorAdapter(@NonNull ActionCallback callback, Context context)
    {
        _callback = callback;
        _context = context;
    }

    @Override
    public int getCount() {
        return _employees.length;
    }

    public void setEmployees(@NonNull Employee[] employees) {
        this._employees = employees;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final Employee capster = _employees[position];

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(_context);
            view = layoutInflater.inflate(R.layout.item_operator,null);

            final TextView initialName = (TextView)view.findViewById(R.id.operatorName);
            final  TextView nickName = (TextView) view.findViewById(R.id.nickName);

            final ViewHolder viewHolder = new ViewHolder(initialName, nickName);
            view.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder._initialName.setText(capster.getInitialName());
        viewHolder._nickName.setText(capster.getFullName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Employee emp = _employees[position];
                _callback.onSelect(emp);
            }
        });

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    private  class ViewHolder
    {
       private final TextView _initialName;
       private  final TextView _nickName;
       public ViewHolder (TextView initialName, TextView nickName){
            _initialName = initialName;
            _nickName = nickName;
       }
    }

    public void setOperator( Employee[] employees){
        _employees = employees;
    }
}
