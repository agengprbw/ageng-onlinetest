package com.test.agengtest;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoDetailActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView completedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        titleTextView = findViewById(R.id.titleTextView);
        completedTextView = findViewById(R.id.completedTextView);

        int todoId = getIntent().getIntExtra("TODO_ID", 0);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Todo> call = apiService.getTodoDetail(todoId);
        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()) {
                    Todo todo = response.body();
                    if (todo != null) {
                        titleTextView.setText(todo.getTitle());
                        String status = todo.isCompleted() ? "Completed" : "Not Completed";
                        int color = todo.isCompleted() ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red);
                        completedTextView.setText(getColoredSpan(status, color));
                    }
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
            }
        });
    }

    private SpannableString getColoredSpan(String text, int color) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(color), 0, text.length(), 0);
        return spannableString;
    }
}
