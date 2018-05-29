package com.example.cyl.alltest;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sohu.kan.R;
import com.sohu.kan.common.utils.SnsUtil;
import com.sohu.kan.common.utils.glide.GlideUtils;
import com.sohu.kan.common.view.emotion.lib.ThemeUtility;
import com.sohu.kan.common.view.imageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yalingcai on 2017/11/8.
 */

public class rv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TITLE_VIEW_TYPE = 0;
    private static final int MONEY_VIEW_TYPE = 1;
    private static final int REFUSE_VIEW_TYPE = 2;
    private static final int REDPACKET_VIEW_TYPE = 3;
    private static final int NORMAL_VIEW_TYPE = 4;


    private boolean isShowRefundView;
    private boolean isShowRedpacketView;
    private Context mContext;
    private List<String> title = new ArrayList<String>();
    private List<String> content = new ArrayList<String>();
    private View.OnClickListener onClickListener;

    private ColorFilter mMaskNightFilter = new PorterDuffColorFilter(0x80ffffff, PorterDuff.Mode.MULTIPLY);

    public rv(Context mContext, List<String> title, List<String> content, boolean isShowRefundView, boolean isShowRedpacketView) {
        this.mContext = mContext;
        this.title = title;
        this.content = content;
        this.isShowRefundView = isShowRefundView;
        this.isShowRedpacketView = isShowRedpacketView;

    }

    public void setData(List<String> title, List<String> content, boolean isShowRefundView, boolean isShowRedpacketView) {
        this.title = title;
        this.content = content;
        this.isShowRefundView = isShowRefundView;
        this.isShowRedpacketView = isShowRedpacketView;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View converTitle = LayoutInflater.from(mContext).inflate(R.layout.sns_wallet_trans_title_item, parent, false);
                TitleViewHolder titleViewHolder = new TitleViewHolder(converTitle);
                return titleViewHolder;
            case 1:
                View converMoney = LayoutInflater.from(mContext).inflate(R.layout.sns_wallet_trans_money_item, parent, false);
                MoneyViewHolder moneyViewHolder = new MoneyViewHolder(converMoney);
                return moneyViewHolder;
            case 2:
                View converRefuse = LayoutInflater.from(mContext).inflate(R.layout.sns_wallet_trans_refund_item, parent, false);
                RefuseViewHolder refuseViewHolder = new RefuseViewHolder(converRefuse);
                return refuseViewHolder;
            case 3:
                View converRedpacket = LayoutInflater.from(mContext).inflate(R.layout.sns_wallet_trans_redpacket_item, parent, false);
                RedpacketViewHolder redpacketViewHolder = new RedpacketViewHolder(converRedpacket);
                return redpacketViewHolder;
            case 4:
                View converNomal = LayoutInflater.from(mContext).inflate(R.layout.sns_wallet_trans_normal_item, parent, false);
                NomalViewHolder nomalViewHolder = new NomalViewHolder(converNomal);
                return nomalViewHolder;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TITLE_VIEW_TYPE;
        } else if (position == 1) {
            return MONEY_VIEW_TYPE;
        }
        if (position == 2) {
            if (isShowRefundView) {
                return REFUSE_VIEW_TYPE;
            } else if (isShowRedpacketView) {
                return REDPACKET_VIEW_TYPE;
            } else {
                return NORMAL_VIEW_TYPE;
            }
        }
        if (position == 3) {
            if (isShowRedpacketView && isShowRefundView) {
                return REDPACKET_VIEW_TYPE;
            } else {
                return NORMAL_VIEW_TYPE;
            }
        } else {
            return NORMAL_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int types = getItemViewType(position);
        switch (types) {
            case 0:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.transTitle.setText(content.get(position));
                //图标
                GlideUtils.setImageBmp(title.get(position), titleViewHolder.transImagin);
                //夜间模式
                if (SnsUtil.isThemeDefaultSimple()) {
                    titleViewHolder.transImagin.setColorFilter(null);
                } else {
                    titleViewHolder.transImagin.setColorFilter(mMaskNightFilter);
                }
                break;
            case 1:
                MoneyViewHolder moneyViewHolder = (MoneyViewHolder) holder;
                moneyViewHolder.transMoneyTitle.setText(title.get(position));
                moneyViewHolder.transMoneyContent.setText(content.get(position));
                break;
            case 2:
                RefuseViewHolder refuseViewHolder = (RefuseViewHolder) holder;
                refuseViewHolder.transRefuseTitle.setText(title.get(position));
                if (isShowRefundView) {
                    String str[] = content.get(position).split("&&", 2);
                    refuseViewHolder.transRefuseContent.setText(str[0]);
                    refuseViewHolder.transRefuseTime.setText(str[1]);
                }
                break;
            case 3:
                RedpacketViewHolder redpacketViewHolder = (RedpacketViewHolder) holder;
                redpacketViewHolder.transRedpacketTitle.setText(title.get(position));
                redpacketViewHolder.transRedpacketCheck.setText(content.get(position));
                redpacketViewHolder.transRedpacketCheck.setOnClickListener(onClickListener);
                break;
            case 4:
                NomalViewHolder nomalViewHolder = (NomalViewHolder) holder;
                nomalViewHolder.transNormalTitle.setText(title.get(position));
                nomalViewHolder.transNormalContent.setText(content.get(position));
                break;
        }

    }


    @Override
    public int getItemCount() {
        if (title != null && title.size() != 0 &&
                content != null && content.size() != 0 && title.size() == content.size()) {
            return title.size();
        } else {
            return 0;
        }
    }

    public void setOnItemClick(View.OnClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    //正常
    public class NomalViewHolder extends RecyclerView.ViewHolder {
        private TextView transNormalTitle;
        private TextView transNormalContent;

        public NomalViewHolder(View itemView) {
            super(itemView);
            transNormalTitle = itemView.findViewById(R.id.tv_trans_normal_title);
            transNormalContent = itemView.findViewById(R.id.tv_trans_normal_content);
            ThemeUtility.setTextViewColor(transNormalTitle, R.color.Blk_4, R.color.Blk_4_night);
            ThemeUtility.setTextViewColor(transNormalContent, R.color.Blk_4, R.color.Blk_4_night);

        }
    }

    //标题
    public class TitleViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView transImagin;
        private TextView transTitle;
        private View dividerTop;

        public TitleViewHolder(View itemView) {
            super(itemView);
            transImagin = itemView.findViewById(R.id.iv_transcation_detail_avatar);
            transTitle = itemView.findViewById(R.id.tv_transcation_detail_title);
            dividerTop = itemView.findViewById(R.id.divider_top);
            ThemeUtility.setTextViewColor(transTitle, R.color.Blk_4, R.color.Blk_4_night);
            ThemeUtility.setColorBackground(dividerTop, R.color.Blk_9, R.color.Blk_9_night);

        }
    }

    //收付款
    public class MoneyViewHolder extends RecyclerView.ViewHolder {
        private TextView transMoneyTitle;
        private TextView transMoneyContent;
        private View dividerMoney;

        public MoneyViewHolder(View itemView) {
            super(itemView);
            transMoneyTitle = itemView.findViewById(R.id.tv_trans_money_title);
            transMoneyContent = itemView.findViewById(R.id.tv_trans_money_content);
            dividerMoney = itemView.findViewById(R.id.divider_money);
            ThemeUtility.setTextViewColor(transMoneyTitle, R.color.Blk_4, R.color.Blk_4_night);
            ThemeUtility.setTextViewColor(transMoneyContent, R.color.Blk_4, R.color.Blk_4_night);
            ThemeUtility.setColorBackground(dividerMoney, R.color.Blk_9, R.color.Blk_9_night);
        }
    }

    //红包退款
    public class RefuseViewHolder extends RecyclerView.ViewHolder {
        private TextView transRefuseTitle;
        private TextView transRefuseContent;
        private TextView transRefuseTime;
        private View dividerRefund;

        public RefuseViewHolder(View itemView) {
            super(itemView);
            transRefuseTitle = itemView.findViewById(R.id.tv_trans_refund_title);
            transRefuseContent = itemView.findViewById(R.id.tv_trans_refund_content);
            transRefuseTime = itemView.findViewById(R.id.tv_trans_refund_time);
            dividerRefund = itemView.findViewById(R.id.divider_refund);
            ThemeUtility.setTextViewColor(transRefuseTitle, R.color.Blk_4, R.color.Blk_4_night);
            ThemeUtility.setTextViewColor(transRefuseTime, R.color.Blk_4, R.color.Blk_4_night);
            ThemeUtility.setTextViewColor(transRefuseContent, R.color.Red_1, R.color.Red_1_night);
            ThemeUtility.setColorBackground(dividerRefund, R.color.Blk_9, R.color.Blk_9_night);
        }
    }

    //查看红包详情
    public class RedpacketViewHolder extends RecyclerView.ViewHolder {
        private TextView transRedpacketTitle;
        private TextView transRedpacketCheck;

        public RedpacketViewHolder(View itemView) {
            super(itemView);
            transRedpacketTitle = itemView.findViewById(R.id.tv_trans_redpacket_title);
            transRedpacketCheck = itemView.findViewById(R.id.tv_trans_redpacket_check);
            ThemeUtility.setTextViewColor(transRedpacketTitle, R.color.Blk_4, R.color.Blk_4_night);
            ThemeUtility.setTextViewColor(transRedpacketCheck, R.color.Blu_1, R.color.Blu_1_night);

        }
    }

}
