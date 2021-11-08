package liram.dev.cryptocurrency.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import liram.dev.cryptocurrency.R;
import liram.dev.cryptocurrency.model.Portfolio;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder> {

    private Context context;
    private List<Portfolio.Purchase> purchases;

    public PortfolioAdapter(Context context, List<Portfolio.Purchase> purchases) {
        this.context = context;
        this.purchases = purchases;
    }

    @NonNull
    @Override
    public PortfolioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.currency_card, parent,false);
        PortfolioViewHolder portfolioViewHolder = new PortfolioViewHolder(itemView);

        return portfolioViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull PortfolioViewHolder holder, int position) {
        Portfolio.Purchase purchase = purchases.get(position);
        String cryptoName = purchase.getPurchaseCrypto().getName();
        holder.cryptoName.setText(cryptoName);
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    class PortfolioViewHolder extends RecyclerView.ViewHolder {

        TextView cryptoName;

        public PortfolioViewHolder(@NonNull View itemView) {
            super(itemView);
            cryptoName = itemView.findViewById(R.id.tvName);

        }
    }
}
