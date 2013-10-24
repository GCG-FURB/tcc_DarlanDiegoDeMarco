package br.com.furb.tagarela.view.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import br.com.furb.tagarela.R;
import br.com.furb.tagarela.utils.listeners.SymbolPositionListener;

public class CreatePlanActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle b = getIntent().getExtras();
		int layout = b.getInt("layout");
		setView(layout);
		super.onCreate(savedInstanceState);
	}

	private void setView(int layout) {
		switch (layout) {
		case 0:
			setContentView(R.layout.plan_layout_zero);
			addImageListener(R.id.L00S00, 0);
			break;
		case 1:
			setContentView(R.layout.plan_layout_one);
			addImageListener(R.id.L01S00, 0);
			addImageListener(R.id.L01S01, 1);
			break;
		case 2:
			setContentView(R.layout.plan_layout_two);
			addImageListener(R.id.L02S00, 0);
			addImageListener(R.id.L02S01, 1);
			addImageListener(R.id.L02S02, 2);
			break;
		case 3:
			setContentView(R.layout.plan_layout_three);
			addImageListener(R.id.L03S00, 0);
			addImageListener(R.id.L03S03, 3);
			break;
		case 4:
			setContentView(R.layout.plan_layout_four);
			addImageListener(R.id.L04S00, 0);
			addImageListener(R.id.L04S01, 1);
			addImageListener(R.id.L04S03, 3);
			addImageListener(R.id.L04S04, 4);
			break;
		case 5:
			setContentView(R.layout.plan_layout_five);
			addImageListener(R.id.L05S00, 0);
			addImageListener(R.id.L05S01, 1);
			addImageListener(R.id.L05S02, 2);
			addImageListener(R.id.L05S03, 3);
			addImageListener(R.id.L05S04, 4);
			addImageListener(R.id.L05S05, 5);
			break;
		case 6:
			setContentView(R.layout.plan_layout_six);
			addImageListener(R.id.L06S00, 0);
			addImageListener(R.id.L06S03, 3);
			addImageListener(R.id.L06S06, 6);
			break;
		case 7:
			setContentView(R.layout.plan_layout_seven);
			addImageListener(R.id.L07S00, 0);
			addImageListener(R.id.L07S01, 1);
			addImageListener(R.id.L07S03, 3);
			addImageListener(R.id.L07S04, 4);
			addImageListener(R.id.L07S06, 6);
			addImageListener(R.id.L07S07, 7);
			break;
		case 8:
			setContentView(R.layout.plan_layout_eight);
			addImageListener(R.id.L08S00, 0);
			addImageListener(R.id.L08S01, 1);
			addImageListener(R.id.L08S02, 2);
			addImageListener(R.id.L08S03, 3);
			addImageListener(R.id.L08S04, 4);
			addImageListener(R.id.L08S05, 5);
			addImageListener(R.id.L08S06, 6);
			addImageListener(R.id.L08S07, 7);
			addImageListener(R.id.L08S08, 8);
			break;
		default:
			break;
		}
	}

	private void addImageListener(int id, int position) {
		((ImageView) findViewById(id)).setOnClickListener(new SymbolPositionListener(position, getSupportFragmentManager()));
	}
}
