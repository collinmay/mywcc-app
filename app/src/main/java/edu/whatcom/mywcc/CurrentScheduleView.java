package edu.whatcom.mywcc;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;

public class CurrentScheduleView extends View {
    private List<CourseScheduleEntry> schedules;
    private int firstHour;
    private int lastHour;
    private DisplayMetrics dm = new DisplayMetrics();
    private Paint gridLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint classSectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint classSectionBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint timePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF classSectionBounds = new RectF();

    public CurrentScheduleView(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    public void init() {
        gridLinePaint.setARGB(55, 0, 0, 0);
        gridLinePaint.setTextSize(30);

        labelPaint.setARGB(200, 0, 0, 0);
        labelPaint.setTextSize(48);
        labelPaint.setTextAlign(Paint.Align.CENTER);

        timePaint.setARGB(200, 0, 0, 0);
        timePaint.setTextSize(30);
        timePaint.setTextAlign(Paint.Align.CENTER);

        classSectionPaint.setARGB(255, 248, 248, 248);
        classSectionPaint.setShadowLayer(5.0f, 0f, 5f, 0x30000000);

        classSectionBorder.setARGB(80, 0, 0, 0);
        classSectionBorder.setStyle(Paint.Style.STROKE);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public void onDraw(Canvas c) {
        float pixelsPerHour = getHeight()/(lastHour-firstHour);
        for(int i = firstHour; i < lastHour; i++) {
            c.drawText(formatTime(i, 0), 10, (i-firstHour)*pixelsPerHour-10, gridLinePaint);
            c.drawLine(0, (i-firstHour)*pixelsPerHour, getWidth(), (i-firstHour)*pixelsPerHour, gridLinePaint);
        }

        for(CourseScheduleEntry e : schedules) {
            float beginHeight = ((e.schedule.startHour - firstHour) * 60.0f + e.schedule.startMinute) * pixelsPerHour / 60.0f;
            float endHeight = ((e.schedule.endHour - firstHour) * 60.0f + e.schedule.endMinute) * pixelsPerHour / 60.0f;
            classSectionBounds.left = 0;
            classSectionBounds.top = beginHeight;
            classSectionBounds.right = getWidth();
            classSectionBounds.bottom = endHeight;
            c.drawRoundRect(classSectionBounds, 10, 10, classSectionPaint);
            c.drawRoundRect(classSectionBounds, 10, 10, classSectionBorder);
            c.drawText(e.course.courseId, getWidth()/2f, (beginHeight+endHeight)/2f-25f, labelPaint);
            c.drawText(e.course.title, getWidth()/2f, (beginHeight+endHeight)/2f+25f, labelPaint);
            c.drawText(e.schedule.room.toString(), getWidth()/2f, (beginHeight+endHeight)/2f+75f, labelPaint);
            c.drawText(formatTime(e.schedule.startHour, e.schedule.startMinute), getWidth()/2f, beginHeight+30, timePaint);
            c.drawText(formatTime(e.schedule.endHour, e.schedule.endMinute), getWidth()/2f, endHeight-10, timePaint);
        }
    }

    private String formatTime(int h, int m) {
        return String.format("%d:%02d %s", (h-1)%12+1, m, h >= 12 ? "PM" : "AM");
    }

    public void setSchedules(List<CourseScheduleEntry> schedules, int firstHour, int lastHour) {
        this.schedules = schedules;
        this.firstHour = firstHour;
        this.lastHour = lastHour;
    }
}
