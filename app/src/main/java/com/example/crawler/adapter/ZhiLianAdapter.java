package com.example.crawler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.crawler.R;
import com.example.crawler.bean.ZhiLianBean;

import java.util.ArrayList;

/**
 * Created by jingxiongdi on 2018/4/17.
 */

public class ZhiLianAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<ZhiLianBean> zhiLianBeans = null;

    public ZhiLianAdapter(Context c,ArrayList list){
        mContext = c;
        zhiLianBeans = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return zhiLianBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return zhiLianBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            if (viewHolder != null)
            {
                convertView = mInflater.inflate(R.layout.list_item_zhilian, null);
                viewHolder.companyName = (TextView) convertView.findViewById(R.id.company_name);
                viewHolder.salary = (TextView) convertView.findViewById(R.id.salary);
                viewHolder.jobs = (TextView) convertView.findViewById(R.id.jobs);
                viewHolder.responsibilities = (TextView) convertView.findViewById(R.id.responsibilities);
                convertView.setTag(viewHolder);
            }
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ZhiLianBean zhiLianBean = zhiLianBeans.get(position);
        viewHolder.companyName.setText(zhiLianBean.getCompanyName());
        viewHolder.jobs.setText(zhiLianBean.getJobs());
        viewHolder.salary.setText(zhiLianBean.getSalary());
        viewHolder.responsibilities.setText(zhiLianBean.getResponsibilities());
        return convertView;
    }

    private static class ViewHolder
    {
        private TextView companyName;
        private TextView jobs;
        private TextView salary;
        private TextView responsibilities;
    }
}
