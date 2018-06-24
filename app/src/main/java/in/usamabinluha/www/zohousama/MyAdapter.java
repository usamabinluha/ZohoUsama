package in.usamabinluha.www.zohousama;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<User> users;
    private Context context;

    public MyAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final User currentUser = users.get(position);
        String formattedMagnitude = formatMagnitude(currentUser.getId());
        holder.idView.setText(formattedMagnitude);
        int magnitudeColor = getMagnitudeColor(currentUser.getId());
        holder.magnitudeCircle.setColor(magnitudeColor);
        holder.firstNameView.setText(currentUser.getFirstName());
        holder.lastNameView.setText(" " + currentUser.getLastName());
        Picasso.get().load(Uri.parse(currentUser.getUrl())).networkPolicy(NetworkPolicy.OFFLINE)
                .fit().centerCrop()
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(Uri.parse(currentUser.getUrl()))
                                .fit().centerCrop().into(holder.imageView);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView idView, firstNameView, lastNameView;
        ImageView imageView;
        GradientDrawable magnitudeCircle;

        public ViewHolder(View itemView) {
            super(itemView);

            idView = (TextView) itemView.findViewById(R.id.id);
            firstNameView = (TextView) itemView.findViewById(R.id.first_name);
            lastNameView = (TextView) itemView.findViewById(R.id.last_name);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            magnitudeCircle = (GradientDrawable) idView.getBackground();
        }
    }

    private String formatMagnitude(int id) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0");
        return magnitudeFormat.format(id);
    }

    private int getMagnitudeColor(int magnitude) {
        int magnitudeColorResourceId;
        switch (magnitude) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }
}
