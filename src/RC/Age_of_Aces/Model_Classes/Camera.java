package RC.Age_of_Aces.Model_Classes;/*
 * Name: Robert Ciborowski
 * Date: 2017-05-03
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.Math.Rect;

public class Camera {
    Rect view;
    private float viewWidthInTiles, viewHeightInTiles;

    public Camera() {
        view = new Rect(0, 0, 0, 0);
        viewWidthInTiles = 0;
        viewHeightInTiles = 0;
    }

    public Rect getView() {
        return view;
    }

    public void setView(Rect view) {
        this.view = view;
    }

    public float getViewWidthInTiles() {
        return viewWidthInTiles;
    }

    public void setViewWidthInTiles(float viewWidthInTiles) {
        this.viewWidthInTiles = viewWidthInTiles;
    }

    public float getViewHeightInTiles() {
        return viewHeightInTiles;
    }

    public void setViewHeightInTiles(float viewHeightInTiles) {
        this.viewHeightInTiles = viewHeightInTiles;
    }
}
