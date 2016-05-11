package com.example.albumart;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * @author Johanes Soetanto
 * @since 10/05/16
 */
public class MainActivity extends AppCompatActivity {

  private ActionBarDrawerToggle menuDrawerToggle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initDrawer();

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.content_frame, new MainFragment())
          .commit();
    }
  }

  private void initDrawer() {
    final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    this.menuDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
        null, R.string.menu_open, R.string.menu_close) {

      @Override
      public void onDrawerClosed(View view) {
        super.onDrawerClosed(view);
        invalidateOptionsMenu();
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        invalidateOptionsMenu();
      }
    };

    drawerLayout.addDrawerListener(menuDrawerToggle);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  protected void onPostCreate(final Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    menuDrawerToggle.syncState();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    final int id = item.getItemId();
    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
