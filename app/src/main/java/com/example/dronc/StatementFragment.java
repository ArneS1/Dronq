package com.example.dronc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StatementFragment extends Fragment {
    private TextView statement_text;
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statement,container, false);
        statement_text = v.findViewById(R.id.statement_text);
        webView = v.findViewById(R.id.webView);
        return v;
    }

    public void updateStatement(String newStatment){
        statement_text.setText(newStatment);
    }
    public String getText(){
        return statement_text.getText().toString();
    }
    public void updateWebView(String url){
        webView.setWebViewClient(new mWebVIewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
    private class mWebVIewClient extends WebViewClient {
    }
}
