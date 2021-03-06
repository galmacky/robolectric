package org.robolectric.android.controller;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import org.robolectric.Robolectric;
import org.robolectric.ShadowsAdapter;

/**
 * Controller class for driving fragment lifecycles, similar to {@link ActivityController}.
 */
public class FragmentController<F extends Fragment> extends ComponentController<FragmentController<F>, F> {
  private final F fragment;
  private final ActivityController<? extends Activity> activityController;

  public static <F extends Fragment> FragmentController<F> of(F fragment) {
    return of(fragment, FragmentControllerActivity.class, null);
  }

  public static <F extends Fragment> FragmentController<F> of(F fragment, Class<? extends Activity> activityClass) {
    return of(fragment, activityClass, null);
  }

  public static <F extends Fragment> FragmentController<F> of(F fragment, Intent intent) {
    return new FragmentController<>(Robolectric.getShadowsAdapter(), fragment, FragmentControllerActivity.class, intent);
  }

  public static <F extends Fragment> FragmentController<F> of(F fragment, Class<? extends Activity> activityClass, Intent intent) {
    return new FragmentController<>(Robolectric.getShadowsAdapter(), fragment, activityClass, intent);
  }

  private FragmentController(ShadowsAdapter shadowsAdapter, F fragment, Class<? extends Activity> activityClass, Intent intent) {
    super(shadowsAdapter, fragment, intent);
    this.fragment = fragment;
    this.activityController = Robolectric.buildActivity(activityClass, intent);
  }

  /**
   * Creates the activity with {@link Bundle} and adds the fragment to the view with ID {@code contentViewId}.
   */
  public FragmentController<F> create(final int contentViewId, final Bundle bundle) {
    shadowMainLooper.runPaused(new Runnable() {
      @Override
      public void run() {
        activityController.create(bundle).get().getFragmentManager().beginTransaction().add(contentViewId, fragment).commit();
      }
    });
    return this;
  }

  /**
   * Creates the activity with {@link Bundle} and adds the fragment to it. Note that the fragment will be added to the view with ID 1.
   */
  public FragmentController<F> create(Bundle bundle) {
    return create(1, bundle);
  }

  @Override
  public FragmentController<F> create() {
    return create(null);
  }

  @Override
  public FragmentController<F> destroy() {
    shadowMainLooper.runPaused(new Runnable() {
      @Override
      public void run() {
        activityController.destroy();
      }
    });
    return this;
  }

  public FragmentController<F> start() {
    shadowMainLooper.runPaused(new Runnable() {
      @Override
      public void run() {
        activityController.start();
      }
    });
    return this;
  }

  public FragmentController<F> resume() {
    shadowMainLooper.runPaused(new Runnable() {
      @Override
      public void run() {
        activityController.resume();
      }
    });
    return this;
  }

  public FragmentController<F> pause() {
    shadowMainLooper.runPaused(new Runnable() {
      @Override
      public void run() {
        activityController.pause();
      }
    });
    return this;
  }

  public FragmentController<F> visible() {
    shadowMainLooper.runPaused(new Runnable() {
      @Override
      public void run() {
        activityController.visible();
      }
    });
    return this;
  }

  public FragmentController<F> stop() {
    shadowMainLooper.runPaused(new Runnable() {
      @Override
      public void run() {
        activityController.stop();
      }
    });
    return this;
  }

  public FragmentController<F> saveInstanceState(final Bundle outState) {
    shadowMainLooper.runPaused(new Runnable() {
      @Override
      public void run() {
        activityController.saveInstanceState(outState);
      }
    });
    return this;
  }

  private static class FragmentControllerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      LinearLayout view = new LinearLayout(this);
      view.setId(1);

      setContentView(view);
    }
  }
}
