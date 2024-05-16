package com.test.agengtest;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoClickListener {
    private RecyclerView recyclerView;
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiService service = RetrofitClient.getClient().create(ApiService.class);
        Call<List<Todo>> call = service.getTodos();
        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<Todo> todos = response.body();
                adapter = new TodoAdapter(todos);
                adapter.setOnTodoClickListener(MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                // handle failure
            }
        });
    }

    @Override
    public void onTodoClick(int position) {
        Todo todo = adapter.getItem(position);
        int todoId = todo.getId();

        Intent intent = new Intent(MainActivity.this, TodoDetailActivity.class);
        intent.putExtra("TODO_ID", todoId);
        startActivity(intent);
    }
}
