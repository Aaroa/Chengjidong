package com.library.IUtils.Dialog;

import com.lidrary.Iutil.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class IEditTextDialog extends IMessageDialog {

	private View view;

	private EditText mEditText;

	@SuppressLint("InflateParams")
	public IEditTextDialog(Context context) {
		super(context);
		view = LayoutInflater.from(context).inflate(
				R.layout.common_dialog_edit_layout, null);
		mEditText = (EditText) view.findViewById(R.id.dialog_edit);
		
	}

	@Override
	public View createContentView() {
		return view;
	}

	public String getEditText() {
		return mEditText.getText().toString();
	}

}
