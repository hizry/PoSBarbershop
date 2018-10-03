package com.akarvisi.barbercamp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akarvisi.barbercamp.R;
import com.akarvisi.barbercamp.domain.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hizry on 3/21/2018.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private final ActionCallback _callback;
    private Context _context;
    private List<Employee> _capster = new ArrayList<>();


    public  EmployeeAdapter(@NonNull ActionCallback callback, Context context)
    {
        _callback = callback;
        _context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false );
        final  ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Employee item_model= _capster.get(position);
        holder.fullName.setText(item_model.getFullName());
        holder.initialName.setText(item_model.getInitialName());
        holder.birthDate.setText(item_model.getBirthDate());


//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                _callback.onEdit(_products.get(holder.getAdapterPosition()));
//            }
//        });
//
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    builder = new AlertDialog.Builder(_context, android.R.style.Theme_Material_Dialog_Alert);
//                } else {
//                    builder = new AlertDialog.Builder(_context);
//                }
//                builder.setTitle("Delete entry")
//                        .setMessage("Are you sure you want to delete this entry?")
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // continue with delete
//                                _callback.deleteProduct(_products.get(holder.getAdapterPosition()));
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // do nothing
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return _capster.size();
    }

    public  Employee getEmployee(int position){return
            _capster.get(position);}

    public void setProducts(@NonNull List<Employee> capsters) {
        this._capster = capsters;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, initialName, birthDate;
        ViewHolder(View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.FullName);
            initialName= (TextView) itemView.findViewById(R.id.InitialName);
            birthDate = (TextView) itemView.findViewById(R.id.BirthDate);
        }
    }

    public interface ActionCallback {
        void onEdit(Employee product);
        void deleteProduct(Employee product);
    }


}
