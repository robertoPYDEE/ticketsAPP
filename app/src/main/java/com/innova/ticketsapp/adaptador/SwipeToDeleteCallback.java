package com.innova.ticketsapp.adaptador;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.innova.ticketsapp.R;

public class SwipeToDeleteCallback extends ItemTouchHelper.Callback {

    Context mContext;
    private Paint mClearPaint;
    private ColorDrawable mBackground;
    private int backgroundColor;
    private Drawable deleteDrawable;
    private int intrinsicWidth;
    private int intrinsicHeight;


    public SwipeToDeleteCallback(Context context) {
        mContext = context;
        mBackground = new ColorDrawable();
        //backgroundColor = Color.parseColor("#D81B60");
        backgroundColor = Color.parseColor("#ffffff");
        mClearPaint = new Paint();
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        deleteDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_delete);
        intrinsicWidth = deleteDrawable.getIntrinsicWidth();
        intrinsicHeight = deleteDrawable.getIntrinsicHeight();


    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }



    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        try {
            View itemView = viewHolder.itemView;
            int itemHeight = itemView.getHeight();

            boolean isCancelled = dX == 0 && !isCurrentlyActive;
/*
        if (isCancelled) {
            clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }*/

            mBackground.setColor(backgroundColor);
            int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
            int deleteIconBottom = deleteIconTop + intrinsicHeight;

            if (dX > 0) {
                int deleteIconLeft = itemView.getLeft() + deleteIconMargin;
                int deleteIconRight = itemView.getLeft() + deleteIconMargin + intrinsicWidth;
                deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                mBackground.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (int) dX, itemView.getBottom());

            }
            if (dX < 0) {
                int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
                int deleteIconRight = itemView.getRight() - deleteIconMargin;
                deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                mBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());

            }


            mBackground.draw(c);
            deleteDrawable.draw(c);

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }catch(Exception e){
            e.printStackTrace();
        }

    }



    private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
        c.drawRect(left, top, right, bottom, mClearPaint);

    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.7f;
    }

    /*
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20; //so background is behind the rounded corners of itemView

        int iconMargin = (itemView.getHeight() - deleteDrawable.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() -deleteDrawable.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + deleteDrawable.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin + deleteDrawable.getIntrinsicWidth();
            int iconRight = itemView.getLeft() + iconMargin;
            deleteDrawable.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            mBackground.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - deleteDrawable.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            deleteDrawable.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            mBackground.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            mBackground.setBounds(0, 0, 0, 0);
        }

        mBackground.draw(c);
        deleteDrawable.draw(c);

    }

     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }
}
