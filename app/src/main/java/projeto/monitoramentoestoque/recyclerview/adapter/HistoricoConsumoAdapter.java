package projeto.monitoramentoestoque.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.model.Consumo;
import projeto.monitoramentoestoque.model.Insumo;

public class HistoricoConsumoAdapter extends RecyclerView.Adapter<HistoricoConsumoAdapter.ConsumoViewHolder> {
    private final List<Consumo> historicoConsumo;
    private final Context context;
    private final Insumo insumo;

    public HistoricoConsumoAdapter(Context context, List<Consumo> historicoConsumo, Insumo insumo) {
        this.historicoConsumo = historicoConsumo;
        this.context = context;
        this.insumo = insumo;
    }

    @NonNull
    @Override
    public HistoricoConsumoAdapter.ConsumoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context). inflate(R.layout.item_consumo, parent, false);
        return new HistoricoConsumoAdapter.ConsumoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsumoViewHolder holder, int position) {
        Consumo consumo = historicoConsumo.get(position);
        holder.vincular(consumo);
    }

    @Override
    public int getItemCount() {
        return historicoConsumo.size();
    }

    public class ConsumoViewHolder extends RecyclerView.ViewHolder{
        private final TextView data;
        private final TextView quantidade;
        private Consumo consumo;

        public ConsumoViewHolder(View itemView){
            super(itemView);
            data = itemView.findViewById(R.id.cardview_consumo_data);
            quantidade = itemView.findViewById(R.id.cardview_consumo_quantidade);
        }

        private void vincular(Consumo consumo){
            this.consumo = consumo;
            preencheCampos(consumo);
        }

        private void preencheCampos(Consumo consumo) {
            data.setText(consumo.getData());
            quantidade.setText(consumo.getQuantidade() + " " );
        }

    }
    private void adiciona (Consumo consumo){
        historicoConsumo.add(consumo);
        notifyDataSetChanged();
    }
}
