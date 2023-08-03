package com.bignerdranch.android.androiddrawingpad

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Canvas
import android.graphics.Path

class MainActivity : AppCompatActivity() {

    private lateinit var drawingView: DrawingView
    private lateinit var colorButtonRed: ImageButton
    private lateinit var colorButtonGreen: ImageButton
    private lateinit var colorButtonBlue: ImageButton
    private lateinit var colorButtonBlack: ImageButton
    private lateinit var colorButtonYellow: ImageButton
    private lateinit var lineThicknessSmall: ImageButton
    private lateinit var lineThicknessMedium: ImageButton
    private lateinit var lineThicknessLarge: ImageButton
    private lateinit var clearButton: ImageButton


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = DrawingView(this)
        val drawingArea: FrameLayout = findViewById(R.id.drawingArea)
        drawingArea.addView(drawingView)

        colorButtonRed = findViewById(R.id.colorButtonRed)
        colorButtonGreen = findViewById(R.id.colorButtonGreen)
        colorButtonBlue = findViewById(R.id.colorButtonBlue)
        colorButtonBlack = findViewById(R.id.colorButtonBlack)
        colorButtonYellow = findViewById(R.id.colorButtonYellow)
        lineThicknessSmall = findViewById(R.id.lineThicknessSmall)
        lineThicknessMedium = findViewById(R.id.lineThicknessMedium)
        lineThicknessLarge = findViewById(R.id.lineThicknessLarge)
        clearButton = findViewById(R.id.clearButton)

        // Set click listeners for color and line thickness buttons
        colorButtonRed.setOnClickListener { drawingView.setCurrentColor(Color.RED) }
        colorButtonGreen.setOnClickListener { drawingView.setCurrentColor(Color.GREEN) }
        colorButtonBlue.setOnClickListener { drawingView.setCurrentColor(Color.BLUE) }
        colorButtonBlack.setOnClickListener { drawingView.setCurrentColor(Color.BLACK) }
        colorButtonYellow.setOnClickListener { drawingView.setCurrentColor(Color.YELLOW) }

        lineThicknessSmall.setOnClickListener { drawingView.setCurrentLineWidth(5f) }
        lineThicknessMedium.setOnClickListener { drawingView.setCurrentLineWidth(10f) }
        lineThicknessLarge.setOnClickListener { drawingView.setCurrentLineWidth(20f) }

        clearButton.setOnClickListener { drawingView.clearCanvas() }
    }
}


class DrawingView(context: Context) : View(context) {

    private var currentColor = Color.BLACK
    private var currentLineWidth = 5f
    private val path = Path()
    private val paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.color = currentColor
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = currentLineWidth
    }

    fun setCurrentColor(color: Int) {
        currentColor = color
        paint.color = currentColor
    }

    fun setCurrentLineWidth(lineWidth: Float) {
        currentLineWidth = lineWidth
        paint.strokeWidth = currentLineWidth
    }

    fun clearCanvas() {
        path.reset()
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
                // Save the completed path and reset the path for the next drawing
                // This prevents all paths from being connected when drawing multiple strokes.
                path.lineTo(x, y)
                path.reset()
            }
        }

        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }
}

