package com.timkontrakan.githubusersearch.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.timkontrakan.githubusersearch.R;
import com.timkontrakan.githubusersearch.model.Users;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<Users> usersList;
    private OnItemClickCallback onItemClickCallback;

    public UserAdapter() {
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(usersList.get(position));
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final CircleImageView circleImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvUserName);
            circleImageView = itemView.findViewById(R.id.ivUserAvatar);
        }

        public void bind(Users users) {
            textView.setText(users.getLogin());
            Glide.with(itemView.getContext())
                    .load(users.getAvatarUrl())
                    .apply(new RequestOptions().override(60, 60))
                    .into(circleImageView);

            itemView.setOnClickListener(v ->
                    onItemClickCallback.onItemClicked(usersList.get(getAdapterPosition()))
            );
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Users data);
    }
}
