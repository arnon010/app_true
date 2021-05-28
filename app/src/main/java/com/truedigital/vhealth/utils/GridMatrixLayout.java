package com.truedigital.vhealth.utils;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.GridMatrixDataListObject;
import com.truedigital.vhealth.model.GridMatrixDataObject;
import com.truedigital.vhealth.model.GridMatrixRowHeaderObject;

import java.util.ArrayList;
import java.util.Collections;

//https://www.androidcode.ninja/android-scroll-table-fixed-header-column/
public class GridMatrixLayout<T> extends RelativeLayout {

    private Context context;

    private ArrayList<String> columHeader;
    private ArrayList<GridMatrixRowHeaderObject> rowHeader;
    private ArrayList<Integer> columHeaderCellsWidth = new ArrayList<>();
    private ArrayList<T> dataList;
    private AppConstants.GridMatrixType matrixType;

    private int rowWidth;
    private int columnWidth;
    private int headerRowStyle = R.style.ehr_TextStyle_Title_Green;
    private int headerColumnStyle = R.style.ehr_TextStyle_SubTitle_Green;
    private int bodyStyle = R.style.ehr_TextStyle_Title_Sub;
    private int itemImageWidth = 150;
    private int itemImageHeight = 150;
    private int gridColor = R.color.color_green;
    private int imageActive;
    private int imageInActive;
    private int marginsRowLeft = 0;
    private int marginsRowTop = 0;
    private int marginsRowRight = 0;
    private int marginsRowBottom = 0;
    private int headerTextGravity = Gravity.LEFT;
    private int marginsItemLeft = 0;
    private int marginsItemTop = 0;
    private int marginsItemRight = 0;
    private int marginsItemBottom = 0;

    private TableLayout firstTable;
    private TableLayout columnTable;
    private TableLayout rowTable;
    private TableLayout dataTable;
    private HorizontalScrollView columnHorizontalScrollView;
    private HorizontalScrollView dataHorizontalScrollView;
    private ScrollView rowVerticalScrollView;
    private ScrollView dataVerticalScrollView;

    private GridMatrixLayout.OnItemClickListener<T> onItemClickListener;
    private GridMatrixLayout.OnRowHeaderClickListener<T> onRowHeaderClickListener;

    public GridMatrixLayout(Context context) {
        super(context);
        this.context = context;
    }

    public GridMatrixLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public GridMatrixLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void initialize(int columnWidth, int rowWidth, ArrayList<String> columHeader, ArrayList<GridMatrixRowHeaderObject> rowHeader, ArrayList<T> dataList, AppConstants.GridMatrixType matrixType) {
        this.columnWidth = columnWidth;
        this.rowWidth = rowWidth;
        this.matrixType = matrixType;
        this.setColumHeader(columHeader);
        this.setRowHeader(rowHeader);
        this.setDataList(dataList);
        this.initComponents();
        this.firstTableGenerateRow();
        this.columnTableGenerateRow();
        this.resizeColumnHeaderHeight();
        this.setColumnHeaderCellWidth();
        this.rowTableGenerateRow();
        this.dataTableGenerateRow();
        this.resizeBodyTableRowHeight();
    }

    public void setHeaderRowStyle(int headerRowStyle) {
        this.headerRowStyle = headerRowStyle;
    }

    public void setHeaderColumnStyle(int headerColumnStyle) {
        this.headerColumnStyle = headerColumnStyle;
    }

    public void setBodyStyle(int bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public void setGridColor(int color) {
        this.gridColor = color;
    }

    public void setItemImage(int width, int height, int imageActive, int imageInActive) {
        this.itemImageWidth = width;
        this.itemImageHeight = height;
        this.imageActive = imageActive;
        this.imageInActive = imageInActive;
    }

    public void setRowHeaderTextGravity(int gravity) {
        this.headerTextGravity = gravity;
    }

    public void setMarginsRow(int left, int top, int right, int bottom) {
        this.marginsRowLeft = left;
        this.marginsRowTop = top;
        this.marginsRowRight = right;
        this.marginsRowBottom = bottom;
    }

    public void setPaddingItem(int left, int top, int right, int bottom) {
        this.marginsItemLeft = left;
        this.marginsItemTop = top;
        this.marginsItemRight = right;
        this.marginsItemBottom = bottom;
    }

    private void setColumHeader(ArrayList<String> columHeader) {
        columHeader.add(0, ""); // first column header and row header
        this.columHeader = columHeader;
    }

    private void setRowHeader(ArrayList<GridMatrixRowHeaderObject> rowHeader) {
        this.rowHeader = rowHeader;
    }

    private void setDataList(ArrayList<T> dataList) {
        this.dataList = dataList;
    }

    private void initComponents() {

        this.firstTable = new TableLayout(this.context);
        this.columnTable = new TableLayout(this.context);
        this.rowTable = new TableLayout(this.context);
        this.dataTable = new TableLayout(this.context);

        //set scroll
        this.columnHorizontalScrollView = new GridMatrixHorizontalScrollView(this.context);
        this.dataHorizontalScrollView = new GridMatrixHorizontalScrollView(this.context);
        this.rowVerticalScrollView = new GridMatrixVerticalScrollView(this.context);
        this.dataVerticalScrollView = new GridMatrixVerticalScrollView(this.context);

        //set id
        this.firstTable.setId((int)1);
        this.columnHorizontalScrollView.setId((int)2);
        this.rowVerticalScrollView.setId((int)3);
        this.dataVerticalScrollView.setId((int)4);

        //set tag
        this.columnHorizontalScrollView.setTag("columnHorizontalScrollView");
        this.dataHorizontalScrollView.setTag("dataHorizontalScrollView");
        this.rowVerticalScrollView.setTag("rowVerticalScrollView");
        this.dataVerticalScrollView.setTag("dataVerticalScrollView");

        this.columnHorizontalScrollView.addView(this.columnTable);
        this.rowVerticalScrollView.addView(this.rowTable);
        this.dataVerticalScrollView.addView(this.dataHorizontalScrollView);
        this.dataHorizontalScrollView.addView(this.dataTable);

        //set main view
        RelativeLayout.LayoutParams columnHorizontalScrollView_Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        columnHorizontalScrollView_Params.addRule(RelativeLayout.RIGHT_OF, this.firstTable.getId());

        RelativeLayout.LayoutParams rowVerticalScrollView_Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rowVerticalScrollView_Params.addRule(RelativeLayout.BELOW, this.firstTable.getId());

        RelativeLayout.LayoutParams dataVerticalScrollView_Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        dataVerticalScrollView_Params.addRule(RelativeLayout.RIGHT_OF, this.rowVerticalScrollView.getId());
        dataVerticalScrollView_Params.addRule(RelativeLayout.BELOW, this.columnHorizontalScrollView.getId());

        this.addView(this.firstTable);
        this.addView(this.columnHorizontalScrollView, columnHorizontalScrollView_Params);
        this.addView(this.rowVerticalScrollView, rowVerticalScrollView_Params);
        this.addView(this.dataVerticalScrollView, dataVerticalScrollView_Params);
    }

    private void firstTableGenerateRow() {
        TableRow tableRow = new TableRow(this.context);
        TextView textView = this.headerTextView(this.columHeader.get(0), Gravity.CENTER, 0);
        tableRow.addView(textView);
        this.firstTable.addView(tableRow);
    }

    private void columnTableGenerateRow() {
        TableRow tableRow = new TableRow(this.context);
        int headerFieldCount = this.columHeader.size();

        TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

        for (int i = 1; i <= (headerFieldCount - 1); i++) {
            TextView textView = this.headerTextView(this.columHeader.get(i), Gravity.CENTER, this.headerColumnStyle);
            textView.setLayoutParams(params);
            tableRow.addView(textView);
        }

        this.columnTable.addView(tableRow);
    }

    private void rowTableGenerateRow() {

        int index = 0;
        for (GridMatrixRowHeaderObject textHeader : this.rowHeader) {
            final int position = index;

            TableRow.LayoutParams params = new TableRow.LayoutParams(this.columHeaderCellsWidth.get(0), LayoutParams.MATCH_PARENT);
            params.setMargins(marginsRowLeft, marginsRowTop, marginsRowRight, marginsRowBottom);
            params.gravity = this.headerTextGravity;

            TableRow tableRow = new TableRow(this.context);
            TextView textView = this.headerTextView(textHeader.getText(), Gravity.CENTER_VERTICAL, this.headerRowStyle);
            tableRow.addView(textView, params);
            tableRow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRowHeaderClickListener != null) {
                        onRowHeaderClickListener.onRowHeaderClick(position);
                    }
                }
            });

            this.rowTable.addView(tableRow);
            index++;
        }
    }

    private void dataTableGenerateRow() {
        int mainLoopCount = 0;
        for (T obj : this.dataList) {
            final GridMatrixDataListObject data = (GridMatrixDataListObject) obj;
            TableRow tableRow = new TableRow(this.context);

            int loopCount = ((TableRow) this.columnTable.getChildAt(0)).getChildCount();

            for (int i = 0; i < loopCount; i++) {
                final int index = i;
                TableRow.LayoutParams params = new TableRow.LayoutParams(this.columHeaderCellsWidth.get(i + 1), LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.CENTER_VERTICAL;
                GridMatrixDataObject item = (GridMatrixDataObject) data.getDataItemList().get(i);

                boolean isSetBorderTop = true;
                boolean isSetBorderLeft = true;
                GridMatrixItemRelativeLayout renderItemLayout = new GridMatrixItemRelativeLayout(context);
                renderItemLayout.setBorderColor(gridColor);
                renderItemLayout.setPadding(10, 10, 10, 10);

                boolean isSetEventClickItem = false;

                if (mainLoopCount != 0) {
                    isSetBorderTop = false;
                }
                if (i != 0) {
                    isSetBorderLeft = false;
                }
                renderItemLayout.setDraw(isSetBorderTop, true, isSetBorderLeft, true);

                RelativeLayout.LayoutParams paramsItem = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                paramsItem.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(this.marginsItemLeft, this.marginsItemTop, this.marginsItemRight, this.marginsItemBottom);
                RelativeLayout rel = new RelativeLayout(context);

                if (this.matrixType == AppConstants.GridMatrixType.Text) {
                    TextView textView = this.bodyTextView(item.getText());
                    rel.addView(textView, paramsItem);
                } else if (this.matrixType == AppConstants.GridMatrixType.Image) {
                    if (item.getActive() != null) {
                        isSetEventClickItem = true;
                        paramsItem.width = this.itemImageWidth;
                        paramsItem.height = this.itemImageHeight;
                        ImageView img = this.bodyImage(item.getActive());
                        rel.addView(img, paramsItem);
                    }
                } else {

                    if (item.getActive() != null) {
                        RelativeLayout.LayoutParams paramsItemImage = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                        paramsItemImage.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

                        isSetEventClickItem = true;
                        paramsItemImage.width = this.itemImageWidth;
                        paramsItemImage.height = this.itemImageHeight;
                        ImageView img = this.bodyImage(item.getActive());
                        rel.addView(img, paramsItemImage);
                    }
                    TextView textView = this.bodyTextView(item.getText());
                    rel.addView(textView, paramsItem);
                }

                renderItemLayout.addView(rel, lp);

                if (isSetEventClickItem) {
                    renderItemLayout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            T sendItem = (T) data.getDataItemList().get(index);
                            onItemClickListener.onItemClick(sendItem);
                        }
                    });
                }

                tableRow.addView(renderItemLayout, params);
            }

            this.dataTable.addView(tableRow);
            mainLoopCount++;
        }
    }

    private void resizeColumnHeaderHeight() {

        TableRow firstTableTR = (TableRow) this.firstTable.getChildAt(0);
        TableRow columnTableTR = (TableRow) this.columnTable.getChildAt(0);

        for (int i = 0; i < firstTableTR.getChildCount(); i++) {

            View view = firstTableTR.getChildAt(i);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.width = this.rowWidth;
        }

        for (int i = 0; i < columnTableTR.getChildCount(); i++) {

            View view = columnTableTR.getChildAt(i);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.width = this.columnWidth + this.marginsItemLeft + this.marginsItemRight;
        }

        //set heigth firstTable
        //when set width column, height will change
        int columnTableTRHeight = this.viewHeight(columnTableTR);
        for (int i = 0; i < firstTableTR.getChildCount(); i++) {

            View view = firstTableTR.getChildAt(i);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.height = columnTableTRHeight;
        }
    }

    void resizeBodyTableRowHeight() {

        int tableC_ChildCount = this.rowTable.getChildCount();
        ArrayList<Integer> heightList = new ArrayList<>();
        ArrayList<TableRow> tableRowList = new ArrayList<>();

        for (int i = 0; i < tableC_ChildCount; i++) {

            TableRow rowTableTR = (TableRow) this.rowTable.getChildAt(i);
            TableRow dataTableTR = (TableRow) this.dataTable.getChildAt(i);

            int rowTableHeight = this.viewHeight(rowTableTR);
            int dataTableHeight = this.viewHeight(dataTableTR);

            int finalHeight = rowTableHeight > dataTableHeight ? rowTableHeight : dataTableHeight;
            heightList.add(finalHeight);
            tableRowList.add(dataTableTR);
        }

        for (int i = 0; i < tableC_ChildCount; i++) {
            TableRow rowTableTR = (TableRow) this.rowTable.getChildAt(i);
            this.matchLayoutHeight(rowTableTR, Collections.max(heightList));
            this.matchLayoutHeight(tableRowList.get(i), Collections.max(heightList));
        }
    }

    private void setColumnHeaderCellWidth() {

        int firstTableChildCount = ((TableRow) this.firstTable.getChildAt(0)).getChildCount();
        int columnTableChildCount = ((TableRow) this.columnTable.getChildAt(0)).getChildCount();

        for (int i = 0; i < (firstTableChildCount + columnTableChildCount); i++) {
            if (i == 0) {
                this.columHeaderCellsWidth.add(this.rowWidth);
            } else {
                this.columHeaderCellsWidth.add(this.columnWidth + this.marginsItemLeft + this.marginsItemRight);
            }
        }
    }

    private TextView headerTextView(String label, int gravity, int style) {
        TextView headerTextView = new TextView(this.context);
        if (style != 0) {
            headerTextView.setTextAppearance(this.context, style);
        }
        headerTextView.setText(label);
        headerTextView.setGravity(gravity);

        return headerTextView;
    }

    private TextView bodyTextView(String label) {

        TextView bodyTextView = new TextView(this.context);
        bodyTextView.setTextAppearance(this.context, this.bodyStyle);
        bodyTextView.setText(label);
        bodyTextView.setGravity(Gravity.CENTER);

        return bodyTextView;
    }

    private ImageView bodyImage(Boolean value) {

        ImageView image = new ImageView(this.context);
        if (value) {
            image.setImageResource(imageActive);
        } else {
            image.setImageResource(imageInActive);
        }


        return image;
    }

    private void matchLayoutHeight(TableRow tableRow, int height) {

        int tableRowChildCount = tableRow.getChildCount();

        if (tableRow.getChildCount() == 1) {

            View view = tableRow.getChildAt(0);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.height = (height - (params.bottomMargin + params.topMargin));
            return;
        }

        for (int i = 0; i < tableRowChildCount; i++) {

            View view = tableRow.getChildAt(i);

            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();

            if (!isTheHighestLayout(tableRow, i)) {
                params.height = (height - (params.bottomMargin + params.topMargin));
                return;
            }
        }
    }

    private boolean isTheHighestLayout(TableRow tableRow, int layoutPosition) {

        int tableRowChildCount = tableRow.getChildCount();
        int highestViewPosition = -1;
        int viewHeight = 0;

        for (int i = 0; i < tableRowChildCount; i++) {
            View view = tableRow.getChildAt(i);
            int height = this.viewHeight(view);

            if (viewHeight < height) {
                highestViewPosition = i;
                viewHeight = height;
            }
        }
        return highestViewPosition == layoutPosition;
    }

    private int viewHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    private int viewWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T data);
    }

    public void setOnItemClickListener(GridMatrixLayout.OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = OnItemClickListener;
    }

    public interface OnRowHeaderClickListener<T> {
        void onRowHeaderClick(int position);
    }

    public void setOnRowHeaderClickListener(GridMatrixLayout.OnRowHeaderClickListener OnRowHeaderClickListener) {
        this.onRowHeaderClickListener = OnRowHeaderClickListener;
    }

    private class GridMatrixHorizontalScrollView extends HorizontalScrollView {

        public GridMatrixHorizontalScrollView(Context context) {
            super(context);
            this.setVerticalScrollBarEnabled(false);
            this.setHorizontalScrollBarEnabled(false);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) this.getTag();

            if (tag.equalsIgnoreCase("columnHorizontalScrollView")) {
                dataHorizontalScrollView.scrollTo(l, 0);
            } else {
                columnHorizontalScrollView.scrollTo(l, 0);
            }
        }

    }

    private class GridMatrixVerticalScrollView extends ScrollView {

        public GridMatrixVerticalScrollView(Context context) {
            super(context);
            this.setVerticalScrollBarEnabled(false);
            this.setHorizontalScrollBarEnabled(false);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) this.getTag();

            if (tag.equalsIgnoreCase("rowVerticalScrollView")) {
                dataVerticalScrollView.scrollTo(0, t);
            } else {
                rowVerticalScrollView.scrollTo(0, t);
            }
        }

    }

}
