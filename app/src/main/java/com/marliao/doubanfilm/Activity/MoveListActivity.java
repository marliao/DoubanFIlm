package com.marliao.doubanfilm.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.marliao.doubanfilm.R;
import com.marliao.doubanfilm.Utils.GetDrawable;
import com.marliao.doubanfilm.engine.MyApplication;
import com.marliao.doubanfilm.vo.Detail;
import com.marliao.doubanfilm.vo.Douban;
import com.marliao.doubanfilm.vo.Subjects;

import java.util.List;

public class MoveListActivity extends AppCompatActivity {

    private ImageView ivBackMain;
    private ListView lvMoveList;
    private TextView tvMoveCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_list);
        initUI();
        initAdapter();
    }

    private void initAdapter() {
        Douban douban = MyApplication.getDouban();
        MyAdapter myAdapter = new MyAdapter(douban);
        lvMoveList.setAdapter(myAdapter);
        tvMoveCity.setText(douban.getTitle());
    }

    private void initUI() {
        tvMoveCity = (TextView) findViewById(R.id.tv_move_city);
        ivBackMain = (ImageView) findViewById(R.id.iv_back_main);
        ivBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoveListActivity.this, MainActivity.class));
                finish();
            }
        });

        lvMoveList = (ListView) findViewById(R.id.lv_move_list);
    }

    public class MyAdapter extends BaseAdapter {

        private Douban douban;

        public MyAdapter(Douban douban) {
            this.douban = douban;
        }

        @Override
        public int getCount() {
            return douban.getCount();
        }

        @Override
        public Subjects getItem(int position) {
            return douban.getSubjects().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = View.inflate(MoveListActivity.this, R.layout.move_list_item, null);
                viewHolder.ivMovePicture = (ImageView) convertView.findViewById(R.id.iv_move_picture);
                viewHolder.tvDirector = (TextView) convertView.findViewById(R.id.tv_director);
                viewHolder.tvStarring = (TextView) convertView.findViewById(R.id.tv_starring);
                viewHolder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
                viewHolder.tvDuration = (TextView) convertView.findViewById(R.id.tv_duration);
                viewHolder.tvReleaseDate = (TextView) convertView.findViewById(R.id.tv_releaseDate);
                viewHolder.tvAnotherName = (TextView) convertView.findViewById(R.id.tv_anotherName);
                viewHolder.tvLink = (TextView) convertView.findViewById(R.id.tv_link);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.ivMovePicture.setBackgroundDrawable(GetDrawable.getdrawable(getItem(position).getImages().get(0), MoveListActivity.this));
            viewHolder.tvDirector.setText(getItem(position).getDirectors().get(0).getName());
            List<Detail> castsList = getItem(position).getCasts();
            String casts = null;
            for (int i = 0; i < castsList.size(); i++) {
                casts += castsList.get(i).getName() + "/";
            }
            viewHolder.tvStarring.setText(casts);
            List<String> genresList = getItem(position).getGenres();
            String genres = null;
            for (int i = 0; i < genresList.size(); i++) {
                genres += genresList.get(i) + "/";
            }
            viewHolder.tvType.setText(genres);
            viewHolder.tvDuration.setText(getItem(position).getDurations());
            List<String> pubdatesList = getItem(position).getPubdates();
            String pubdates = null;
            for (int i = 0; i < pubdatesList.size(); i++) {
                pubdates += pubdatesList.get(i) + "/";
            }
            viewHolder.tvReleaseDate.setText(pubdates);
            viewHolder.tvAnotherName.setText(getItem(position).getOriginal_title());
            Spanned htmlLink = Html.fromHtml("<a href='" + getItem(position).getAlt() + "' />");
            viewHolder.tvLink.setText(htmlLink);
            return convertView;
        }
    }

    class ViewHolder {
        ImageView ivMovePicture;
        TextView tvDirector;
        TextView tvStarring;
        TextView tvType;
        TextView tvDuration;
        TextView tvReleaseDate;
        TextView tvAnotherName;
        TextView tvLink;
    }

}
