package vn.name.hohoanghai.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.name.hohoanghai.pdtntt.R;
import vn.name.hohoanghai.utils.ImageUtils;
import vn.name.hohoanghai.utils.Settings;

public class ImageFragment extends Fragment {
    @BindView(R.id.img_holder)
    ImageView imgHolder;

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        unbinder = ButterKnife.bind(this, view);

        ImageUtils.loadImage(getContext(), imgHolder, Settings.URL_LESSION);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}