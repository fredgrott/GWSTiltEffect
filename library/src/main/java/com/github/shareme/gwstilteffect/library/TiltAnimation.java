/*
 * Copyright 2013 biboune
 * Modifications Copyright(C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.shareme.gwstilteffect.library;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("unused")
public class TiltAnimation extends Animation {

	private static final String TAG = "TiltAnimation";

	public static final int ROTATE_AXIS_X = 0;
	public static final int ROTATE_AXIS_Y = 1;

	private final float mCenterX;
	private final float mCenterY;

	private Camera mCamera;

	private final ArrayList<Rotation> mRotations = new ArrayList<>();

	public TiltAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, int rotateAxe) {
		this(centerX, centerY);
		mRotations.add(new Rotation(rotateAxe, fromDegrees, toDegrees));
	}

	public TiltAnimation(float centerX, float centerY) {
		mCenterX = centerX;
		mCenterY = centerY;
	}

	public void addRotation(float fromDegrees, float toDegrees, int rotateAxe) {
		mRotations.add(new Rotation(rotateAxe, fromDegrees, toDegrees));
	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float centerX = mCenterX;
		final float centerY = mCenterY;

		final Matrix matrix = t.getMatrix();

		mCamera.save();

		for(Rotation rotation : mRotations) {
			Log.d(TAG, "rotation : " + rotation);
			float degrees = rotation.mFromDegrees + ((rotation.mToDegrees - rotation.mFromDegrees) * interpolatedTime);
			if(rotation.mRotateAxis == ROTATE_AXIS_X) {
				mCamera.rotateX(degrees);
			} else if(rotation.mRotateAxis == ROTATE_AXIS_Y) {
				mCamera.rotateY(degrees);
			}
		}

		mCamera.getMatrix(matrix);
		mCamera.restore();

		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);

	}

	public void addRotations(Rotation... rotations) {
		mRotations.addAll(Arrays.asList(rotations));
	}


	public static final class Rotation {
		int mRotateAxis;
		float mFromDegrees;
		float mToDegrees;

		public Rotation(int mAxis, float mFromDegrees, float mToDegrees) {
			this.mRotateAxis = mAxis;
			this.mFromDegrees = mFromDegrees;
			this.mToDegrees = mToDegrees;
		}

		@Override
		public String toString() {
			return "Rotation{" +
					"mRotateAxis=" + (mRotateAxis == ROTATE_AXIS_X ? "X":"Y") +
					", mFromDegrees=" + mFromDegrees +
					", mToDegrees=" + mToDegrees +
					'}';
		}
	}
}