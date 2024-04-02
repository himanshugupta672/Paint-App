package com.example.paintapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.paintapp.MainActivity.Companion.paintBrush
import com.example.paintapp.MainActivity.Companion.path

class PaintView : View {

    var params : ViewGroup.LayoutParams? = null
    companion object{
        var pathList = ArrayList<Path>()
        var colorList = ArrayList<Int>()
        var currentBrush = Color.BLACK
    }
    constructor(context: Context) : this(context, null){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private  fun init(){
        paintBrush.isAntiAlias = true
        paintBrush.color= currentBrush
        paintBrush.style = Paint.Style.STROKE

        //BY THIS THE STROKE OF ANY PATH WILL HAVE ROUND FINISH
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f;

        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var X = event.x
        var Y = event.y
        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                path.moveTo(X,Y)
                return  true
            }

            //to draw line when we move touch
            MotionEvent.ACTION_MOVE ->{
                path.lineTo(X,Y)
                pathList.add(path)
                colorList.add(currentBrush)
            }
            else -> return false
        }
        //it tells about the changes done in the UI
        postInvalidate()
        return  false;

    }

    //to draw something on screen
    override fun onDraw(canvas: Canvas) {
        for(i in pathList.indices){
            paintBrush.setColor(colorList[i])
            canvas.drawPath(pathList[i], paintBrush)

            //it tells about the changes done in the UI
            invalidate()
        }
    }

}