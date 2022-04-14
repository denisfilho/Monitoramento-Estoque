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
import projeto.monitoramentoestoque.model.Entrada;
import projeto.monitoramentoestoque.model.Insumo;

public class HistoricoEntradaAdapter extends RecyclerView.Adapter<HistoricoEntradaAdapter.EntradaViewHolder> {
    private final List<Entrada> historicoEntrada;
    private final Context context;
    private final Insumo insumo;

    public HistoricoEntradaAdapter(Context context, List<Entrada> historicoEntrada, Insumo insumo) {
        this.historicoEntrada = historicoEntrada;
        this.context = context;
        this.insumo = insumo;
    }

    @NonNull
    @Override
    public HistoricoEntradaAdapter.EntradaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context). inflate(R.layout.item_entrada_consumo, parent, false);
        return new HistoricoEntradaAdapter.EntradaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoEntradaAdapter.EntradaViewHolder holder, int position) {
        Entrada entrada = historicoEntrada.get(position);
        holder.vincular(entrada);
    }

    @Override
    public int getItemCount() {
        return historicoEntrada.size();
    }

    class EntradaViewHolder extends RecyclerView.ViewHolder {
        private final TextView data;
        private final TextView quantidade;
        private Entrada entrada;

        public EntradaViewHolder(View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.cardview_entrada_data);
            quantidade = itemView.findViewById(R.id.cardview_entrada_quantidade);
        }

        private void vincular(Entrada entrada) {
            this.entrada = entrada;
            preencheCampos(entrada);
        }

        private void preencheCampos(Entrada entrada) {
            data.setText(entrada.getData());
            quantidade.setText(entrada.getQuantidade() + insumo.getUnidade());
        }
    }

    public void adiciona(Entrada entrada){
        historicoEntrada.add(entrada);
        notifyDataSetChanged();
    }

    public void atualiza(List<Entrada> entradas){
        this.historicoEntrada.clear();
        this.historicoEntrada.addAll(entradas);
        notifyDataSetChanged();
    }
}
