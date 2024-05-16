package com.test.agengtest;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> todos;
    private OnTodoClickListener onTodoClickListener;

    public TodoAdapter(List<Todo> todos) {
        this.todos = todos;
    }
    public Todo getItem(int position) {
        return todos.get(position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.titleTextView.setText(todo.getTitle());
        holder.completedCheckBox.setChecked(todo.isCompleted());

        if (todo.isCompleted()) {
            holder.completedCheckBox.setButtonTintList(ColorStateList.valueOf(holder.itemView.getContext().getResources().getColor(R.color.green)));
        } else {
            holder.completedCheckBox.setButtonTintList(ColorStateList.valueOf(holder.itemView.getContext().getResources().getColor(R.color.red)));
        }
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setOnTodoClickListener(OnTodoClickListener onTodoClickListener) {
        this.onTodoClickListener = onTodoClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        CheckBox completedCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            completedCheckBox = itemView.findViewById(R.id.completedCheckBox);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onTodoClickListener != null) {
                onTodoClickListener.onTodoClick(getAdapterPosition());
            }
        }
    }

    public interface OnTodoClickListener {
        void onTodoClick(int position);
    }
}
