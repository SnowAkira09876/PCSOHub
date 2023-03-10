package com.ph.pcsolottowatcher.dialogs.datepicker;

import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.ph.pcsolottowatcher.common.fragment.BaseFragment;
import com.ph.pcsolottowatcher.viewmodels.AllResultsViewModel;
import java.util.Calendar;
import java.util.Locale;

public class DialogFragmentDate extends DialogFragment {
  protected BaseFragment parentFragment;
  private AllResultsViewModel all_results_viewModel;

  @Override
  public void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    this.all_results_viewModel =
        new ViewModelProvider(getActivity()).get(AllResultsViewModel.class);
  }

  @Override
  public Dialog onCreateDialog(Bundle bundle) {
    Calendar start = Calendar.getInstance();
    start.set(2013, Calendar.JANUARY, 1);

    Calendar end = Calendar.getInstance();
    end.set(2022, Calendar.DECEMBER, 31);

    CalendarConstraints constraints =
        new CalendarConstraints.Builder()
            .setStart(start.getTimeInMillis())
            .setEnd(end.getTimeInMillis())
            .build();

    MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
    builder.setCalendarConstraints(constraints);

    MaterialDatePicker<Long> picker = builder.build();
    picker.addOnPositiveButtonClickListener(
        selection -> {
          Calendar calendar = Calendar.getInstance();
          calendar.setTimeInMillis(selection);
          setDate(calendar);
          dismiss();
        });

    picker.addOnDismissListener(
        dialog -> {
          dismiss();
        });

    picker.show(getParentFragmentManager(), picker.toString());

    return super.onCreateDialog(bundle);
  }

  private void setDate(Calendar calendar) {
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    String date = format.format(calendar.getTime());

    all_results_viewModel.setDate(date);
  }
}
