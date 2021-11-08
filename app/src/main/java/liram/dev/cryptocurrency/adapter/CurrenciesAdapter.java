package liram.dev.cryptocurrency.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.util.ArrayList;
import java.util.List;

import liram.dev.cryptocurrency.R;
import liram.dev.cryptocurrency.model.Crypto;
import liram.dev.cryptocurrency.ui.marketcap.currencyDetails.CurrencyDetailsFragment;
import liram.dev.cryptocurrency.utility.Util;


//import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.CurrenciesViewHolder> implements Filterable {

  private List<Crypto> currencies;
  private List<Crypto> filterCryptoList;
  private Context context;


    public CurrenciesAdapter(List<Crypto> currencies, Context context) {
        this.currencies = currencies;
        this.filterCryptoList = currencies;
        this.context = context;
    }

    @NonNull
    @Override
    public CurrenciesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.currency_card, viewGroup, false);
        CurrenciesViewHolder viewHolder = new CurrenciesViewHolder(itemView);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CurrenciesViewHolder currenciesViewHolder, int i) {

        Crypto currency = filterCryptoList.get(i);
        currenciesViewHolder.model = currency;
        currenciesViewHolder.name.setText(currency.getName());
        currenciesViewHolder.shortTitle.setText(currency.getSymbol().toUpperCase());
        String price = Util.priceFormat(currency.getCurrentPrice());
        currenciesViewHolder.price.setText(price);


        String imagePath = currency.getImage();
        //TODO: Place Holder
       if (imagePath.contains(".svg")){
           GlideToVectorYou.init().with(context).load(Uri.parse(imagePath), currenciesViewHolder.ivIcon);
       }else{
           Glide.with(context).load(imagePath).into(currenciesViewHolder.ivIcon);
       }

        double changePercentage24H = currency.getPriceChangePercentage24H();
        if (changePercentage24H < 0){
            String priceChangePercentage24H = String.valueOf(changePercentage24H).substring(0,4);
            currenciesViewHolder.twentyFourHr.setText(priceChangePercentage24H + "%");
            currenciesViewHolder.twentyFourHr.setTextColor(context.getColor(R.color.red));
        }else{
            String priceChangePercentage24H = String.valueOf(changePercentage24H).substring(0,4);
            currenciesViewHolder.twentyFourHr.setText(priceChangePercentage24H + "%");
            currenciesViewHolder.twentyFourHr.setTextColor(context.getColor(R.color.green));
        }


    }

    @Override
    public int getItemCount() {
        return  filterCryptoList.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSearch = (String) charSequence;
                if (charSearch.isEmpty()){
                    filterCryptoList = currencies;
                }else {
                    List<Crypto> filterList = new ArrayList<>();
                    for (Crypto crypto : currencies) {
                        if (crypto.getName().toLowerCase().contains(charSearch.toLowerCase())){
                            filterList.add(crypto);
                        }
                    }
                    filterCryptoList = filterList;
                }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterCryptoList;
                    filterResults.count = filterCryptoList.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                  filterCryptoList = (List<Crypto>) filterResults.values;
                  notifyDataSetChanged();
            }
        };
        return filter;
    }

    class CurrenciesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Crypto model;
        TextView balance;
        TextView name;
        TextView shortTitle;
        TextView price;
        TextView twentyFourHr;
        ImageView ivIcon;
        Button next;

        public CurrenciesViewHolder(@NonNull View itemView) {
            super(itemView);
            initialComponent(itemView);
        }

        private void initialComponent(View view) {
            name = view.findViewById(R.id.tvName);
            shortTitle = view.findViewById(R.id.tv_short_name);
            price = view.findViewById(R.id.tv_price);
            twentyFourHr = view.findViewById(R.id.twenty_four_hr);
            ivIcon = view.findViewById(R.id.ivIcon);

           // balance = view.findViewById(R.id.tv_title);
            view.setOnClickListener(this);

        }

        private void onClickItem(View Item){}


        @Override
        public void onClick(View view) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;

            appCompatActivity.getSupportFragmentManager().beginTransaction().addToBackStack("Market Place").replace(R.id.frame, CurrencyDetailsFragment.newInstance(model)).commit();
        }
    }
}
