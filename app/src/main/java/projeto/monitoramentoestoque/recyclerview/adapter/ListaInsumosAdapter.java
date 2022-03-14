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
import projeto.monitoramentoestoque.model.Insumo;

public class ListaInsumosAdapter extends RecyclerView.Adapter<ListaInsumosAdapter.InsumoViewHolder> {
    private List<Insumo> insumos;
    private Context context;

    public ListaInsumosAdapter(Context context, List<Insumo> insumos) {
        this.insumos = insumos;
        this.context = context;
    }

    @NonNull
    @Override
    public ListaInsumosAdapter.InsumoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context). inflate(R.layout.item_insumo, parent, false);
        return new InsumoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaInsumosAdapter.InsumoViewHolder holder, int position) {
        Insumo insumo = insumos.get(position);
        holder.vincular(insumo);
    }

    @Override
    public int getItemCount() {
        return insumos.size();
    }

    class InsumoViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeInsumo;
        private final TextView estoqueAtualInsumo;

        public InsumoViewHolder(View itemView) {
            super(itemView);
            nomeInsumo = itemView.findViewById(R.id.item_insumo_nome);
            estoqueAtualInsumo = itemView.findViewById(R.id.item_estoque_atual);
        }

        private void vincular(Insumo insumo) {
            nomeInsumo.setText(insumo.getNome());
            estoqueAtualInsumo.setText("Estoque Atual: " + Integer.toString(insumo.getEstoqueAtual()));
        }
    }
}
