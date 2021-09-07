package com.spica.app.ui.webview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityWebviewBinding
import kotlin.math.floor


private const val EXTRA_URL = "extra_url"
private const val EXTRA_TITLE = "extra_title"
private val URL_POSITION_CACHES: java.util.HashMap<String, Int> = HashMap()
class WebActivity : BindingActivity<ActivityWebviewBinding>(),
  ObservableWebView.OnScrollChangedListener {


  private lateinit var url: String



  private var positionHolder = 0

  private var overrideTitleEnabled = true

  companion object{
    fun newIntent(context: Context, extraTitle: String, extraURL: String): Intent {
      val intent = Intent(context, WebActivity::class.java)
      intent.putExtra(EXTRA_TITLE, extraTitle)
      intent.putExtra(EXTRA_URL, extraURL)
      return intent
    }
  }



  override fun onCreate(savedInstanceState: Bundle?) {
    overridePendingTransition(0, 0);
    super.onCreate(savedInstanceState)
  }

  override fun initializer() {
    url = intent.getStringExtra(EXTRA_URL) ?: ""
    var title = intent.getStringExtra(EXTRA_TITLE)

    setSupportActionBar(viewBinding.toolbar)

    val settings: WebSettings = viewBinding.webView.settings
    settings.javaScriptEnabled = true
    settings.loadWithOverviewMode = true
    settings.setAppCacheEnabled(true)
    settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    settings.setSupportZoom(true)
    settings.domStorageEnabled = true
    viewBinding.webView.webChromeClient = ChromeClient()
    viewBinding.webView.webViewClient = ReloadableClient()
    viewBinding.webView.setOnScrollChangedListener(this)
    viewBinding.webView.loadUrl(url)

    viewBinding.textSwitch.setFactory {
      val context: Context = this@WebActivity
      val textView = TextView(context)
      textView.setTextAppearance(context, R.style.WebTitle)
      textView.isSingleLine = true
      textView.ellipsize = TextUtils.TruncateAt.MARQUEE
      textView.setOnClickListener {
        textView.isSelected = !textView.isSelected
      }
      textView
    }
    viewBinding.textSwitch.setInAnimation(this, android.R.anim.fade_in)
    viewBinding.textSwitch.setOutAnimation(this, android.R.anim.fade_out)
    if (title != null) setTitle(title)
  }


  @JvmName("setOverrideTitleEnabled1")
  fun setOverrideTitleEnabled(enabled: Boolean) {
    overrideTitleEnabled = enabled
  }

  override fun setTitle(title: CharSequence?) {
    super.setTitle(title)
    viewBinding.textSwitch.setText(title)
  }

  private fun refresh() {
    viewBinding.webView.reload()
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
    if (event.action === KeyEvent.ACTION_DOWN) {
      when (keyCode) {
        KeyEvent.KEYCODE_BACK -> {
          if (viewBinding.webView.canGoBack()) {
            viewBinding.webView.goBack()
          } else {
            finish()
          }
          return true
        }
      }
    }
    return super.onKeyDown(keyCode, event)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(com.spica.app.R.menu.menu_webview, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_refresh -> {
        refresh()
        return true
      }
      R.id.action_copy_url -> {

        return true
      }
      R.id.action_open_url -> {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(packageManager) != null) {
          startActivity(intent)
        } else {
          makeText(this, "启动浏览器失败", Toast.LENGTH_LONG).show()
        }
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }


  override fun setupViewBinding(inflater: LayoutInflater): ActivityWebviewBinding =
    ActivityWebviewBinding.inflate(inflater)

  override fun onScrollChanged(v: WebView?, x: Int, y: Int, oldX: Int, oldY: Int) {
    positionHolder = y
  }

  override fun finish() {
    super.finish()
    overridePendingTransition(0, 0)
  }


  override fun onDestroy() {
    val webView = findViewById<WebView?>(R.id.web_view)
    val url = webView.url
    val bottom = floor((webView.contentHeight * webView.scale * 0.8f).toDouble()).toInt()
    if (positionHolder >= bottom) {
      URL_POSITION_CACHES.remove(url)
    } else {
      URL_POSITION_CACHES[url ?: ""] = positionHolder
    }
    super.onDestroy()

    webView?.destroy()
  }


  private inner class ChromeClient : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
      super.onProgressChanged(view, newProgress)
      viewBinding.progress.progress = newProgress
      if (newProgress == 100) {
        viewBinding.progress.visibility = View.INVISIBLE
      } else {
        viewBinding.progress.visibility = View.VISIBLE
      }
    }


    override fun onReceivedTitle(view: WebView?, title: String?) {
      super.onReceivedTitle(view, title)
      if (overrideTitleEnabled) {
        setTitle(title)
      }
    }
  }

  private inner class ReloadableClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
      if (url != null) view.loadUrl(url)
      return true
    }


    override fun onPageCommitVisible(view: WebView, url: String?) {
      super.onPageCommitVisible(view, url)
      val _position = URL_POSITION_CACHES[url]
      val position = _position ?: 0
      view.scrollTo(0, position)
    }


    override fun onPageFinished(view: WebView?, url: String) {
      super.onPageFinished(view, url)
    }

  }


}