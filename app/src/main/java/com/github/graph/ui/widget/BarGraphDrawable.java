/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.graph.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.PaintDrawable;
import org.eclipse.egit.github.core.Contributor;

import java.util.List;

/**
 * Drawable bar graph
 */
public class BarGraphDrawable extends PaintDrawable {

    private static final int MIN_HEIGHT = 4;

    private static final int SPACING_X = 1;
    private static final int MARGIN = 20;

    private static final int COLOR = 0x0000FF;

    private static final int MIN_COLOR = 30;

    private long max = 1;

    private List<Contributor> contributors;

    public BarGraphDrawable(final List<Contributor> contributors) {
        super(android.R.color.transparent);
        this.contributors = contributors;
        for (int i = 0; i < contributors.size(); i++)
            max = Math.max(max, contributors.get(i).getContributions());
    }

    final Paint black = new Paint();

    @Override
    public void draw(final Canvas canvas) {
        final Paint paint = getPaint();
        final Rect bounds = getBounds();
        final float width = ((float) bounds.width() / contributors.size()) - SPACING_X;
        final int height = bounds.height() - MARGIN;
        float x = 0;
        for (int i = 0; i < contributors.size(); i++) {
            Contributor c = contributors.get(i);
            float percentage = (float) c.getContributions() / max;
            paint.setColor(calculateColor(percentage));
            float h = MARGIN + height - Math.max(MIN_HEIGHT, percentage * height);
            canvas.drawRect(x, h, x + width, bounds.bottom, paint);
            canvas.drawText(c.getLogin(),x + MIN_HEIGHT, h - MIN_HEIGHT, black);
            x += width + SPACING_X;
        }
    }

    private static int calculateColor(float percent){
        int red = (COLOR >> 16) & 0xFF;
        int green = (COLOR >> 8) & 0xFF;
        int blue = COLOR & 0xFF;

        int p = 100 - Math.max(MIN_COLOR, (int)percent*100);

        red = Math.min(red + (0xFF - red)*p/100, 0xFF);
        green = Math.min(green + (0xFF - green)*p/100, 0xFF);
        blue = Math.min(blue + (0xFF - blue)*p/100, 0xFF);

        return 0xFF000000  | (red << 16) | (green << 8) | blue ;
    }
}