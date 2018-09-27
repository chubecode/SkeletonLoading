package it.chutien.skeletonscreen

import RecipeListAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.shimmer.ShimmerFrameLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    private var mShimerViewContainer: ShimmerFrameLayout? = null
    private var mRecyclerView: RecyclerView? = null

    private lateinit var cartList: ArrayList<Recipe>

    private lateinit var mAdapter: RecipeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fresco.initialize(this)
        mShimerViewContainer = findViewById(R.id.shimmer_view_container)
        mRecyclerView = findViewById(R.id.recycler_view)

        cartList = ArrayList()
        mAdapter = RecipeListAdapter(this, cartList, ItemAnimation.RIGHT_LEFT)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView?.setLayoutManager(mLayoutManager)
        mRecyclerView?.setItemAnimator(DefaultItemAnimator())
//        mRecyclerView?.addItemDecoration(MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16))
        mRecyclerView?.setAdapter(mAdapter)

        // making http call and fetching menu json
        fetchRecipes()
    }

    @SuppressLint("CheckResult")
    private fun fetchRecipes() {
        val apiService: ApiService = ApiService.create()

        apiService.getData().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe({ result ->

            if (result != null) {
                cartList.clear()
                cartList.addAll(result)
                // refreshing recycler view
                mAdapter.notifyDataSetChanged()
                // stop animating Shimmer and hide the layout
                mShimerViewContainer?.stopShimmer();
                mShimerViewContainer?.visibility = View.GONE;
            }


        }, { error ->
            error.printStackTrace()
        })


    }

    override fun onResume() {
        super.onResume()
        mShimerViewContainer?.startShimmer()

    }

    override fun onPause() {
        mShimerViewContainer?.stopShimmer()
        super.onPause()
    }
}
