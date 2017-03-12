package mx.mango.pics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mx.mango.pics.R;
import mx.mango.pics.models.ApiSnap;
import mx.mango.pics.utils.StringUtils;

import static android.R.attr.data;

public class SnapListAdapter extends BaseAdapter {
    List<ApiSnap> snaps;
    Context context;
    LayoutInflater layoutInflater;

    public SnapListAdapter(Context context, List<ApiSnap> snaps) {
        super();
        this.context = context;
        this.snaps = snaps;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return snaps.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ApiSnap snap = snaps.get(position);
        view = layoutInflater.inflate(R.layout.snap_layout_adapter, null);

        TextView tvCause = (TextView) view.findViewById(R.id.tv_snap_cause);
        TextView tvDescription = (TextView) view.findViewById(R.id.tv_snap_description);
        ImageView ivSnap = (ImageView) view.findViewById(R.id.iv_snap);

        tvCause.setText(snap.getCause());
        tvDescription.setText(snap.getDescription());
        ivSnap.setImageBitmap(StringUtils.base64ToBitmap(snap.getPics().get(0)));

        return view;
    }

    public List<ApiSnap> getSnaps() {
        return snaps;
    }
}
