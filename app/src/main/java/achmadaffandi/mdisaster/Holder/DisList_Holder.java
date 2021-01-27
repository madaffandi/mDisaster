package achmadaffandi.mdisaster.Holder;


import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import achmadaffandi.mdisaster.R;

public class DisList_Holder extends RecyclerView.ViewHolder {

    View mView;
    String ikonBencana;

    public DisList_Holder(View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
    }

    private DisList_Holder.ClickListener mClickListener;

    public interface ClickListener {
        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(DisList_Holder.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setTitle(String title) {
        TextView post_title = (TextView) mView.findViewById(R.id.dis_title);
        post_title.setText(title);
    }

    public void setDesc(String desc) {
        TextView post_title = (TextView) mView.findViewById(R.id.dis_desc);
        post_title.setText(desc);
    }

    //menerjemahkan jenis bencana untuk mengatur icon bencana
    public void callImage(Context ctx, String jenisBencana) {
        if (jenisBencana.equals("Banjir")) {
            ikonBencana = "ic_banjir";
            setImage(ctx, ikonBencana);
        } else if (jenisBencana.equals("Erupsi")) {
            ikonBencana = "ic_erupsi";
            setImage(ctx, ikonBencana);
        } else if (jenisBencana.equals("Gempa")) {
            ikonBencana = "ic_gempa";
            setImage(ctx, ikonBencana);
        } else if (jenisBencana.equals("Kebakaran")) {
            ikonBencana = "ic_kebakaran";
            setImage(ctx, ikonBencana);
        } else if (jenisBencana.equals("Tanah Longsor")) {
            ikonBencana = "ic_longsor";
            setImage(ctx, ikonBencana);
        } else {
            ikonBencana = "ic_lain";
            setImage(ctx, ikonBencana);
        }
    }

    //mengatur icon bencana
    public void setImage(Context ctx, String ikonBencana) {
        ImageView post_img = (ImageView) mView.findViewById(R.id.dis_img);
        int id = ctx.getResources().getIdentifier("achmadaffandi.mdisaster:drawable/" + ikonBencana, null, null);
        post_img.setImageResource(id);
    }
}