package projeto.monitoramentoestoque.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import projeto.monitoramentoestoque.ListaInsumosActivity;
import projeto.monitoramentoestoque.R;
import projeto.monitoramentoestoque.model.Insumo;

public class ListaInsumosAdapter extends RecyclerView.Adapter {
    private List<Insumo> insumos;
    private Context context;

    public ListaInsumosAdapter(Context context, List<Insumo> insumos) {
        this.insumos = insumos;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context). inflate(R.layout.item_insumo, parent, false);
        return new InsumoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Insumo insumo = insumos.get(position);
        TextView nomeInsumo = holder.itemView.findViewById(R.id.item_insumo_nome);
        nomeInsumo.setText(insumo.getNome());

        TextView estoqueAtualInsumo = holder.itemView.findViewById(R.id.item_estoque_atual);
        estoqueAtualInsumo.setText("Estoque Atual: " + Integer.toString(insumo.getEstoqueAtual()));
    }

    @Override
    public int getItemCount() {
        return insumos.size();
    }

    class InsumoViewHolder extends RecyclerView.ViewHolder {
        public InsumoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
