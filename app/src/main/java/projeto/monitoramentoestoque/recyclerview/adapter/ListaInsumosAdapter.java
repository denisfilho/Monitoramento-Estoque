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
import projeto.monitoramentoestoque.recyclerview.adapter.listener.OnItemClickListener;
import projeto.monitoramentoestoque.recyclerview.adapter.listener.OnItemLongClickListener;

public class ListaInsumosAdapter extends RecyclerView.Adapter<ListaInsumosAdapter.InsumoViewHolder> {
    private final List<Insumo> insumos;
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public ListaInsumosAdapter(Context context, List<Insumo> insumos) {
        this.insumos = insumos;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
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

    public Insumo getItem(int position){

        return insumos.get(position);
    }

    class InsumoViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeInsumo;
        private final TextView estoqueAtualInsumo;
        private Insumo insumo;

        public InsumoViewHolder(View itemView) {
            super(itemView);
            nomeInsumo = itemView.findViewById(R.id.cardview_item_insumo_nome);
            estoqueAtualInsumo = itemView.findViewById(R.id.cardview_item_insumo_quantidade);
            configuraExibicaoInsumo(itemView);
            configuraRemocaoInsumo(itemView);
        }

        private void configuraExibicaoInsumo(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(insumo);
                }
            });
        }

        private void configuraRemocaoInsumo(View itemView) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemLongClickListener!= null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            onItemLongClickListener.OnItemLongClick(pos);
                        }
                    }
                    return true;
                }
            });
        }

        private void vincular(Insumo insumo) {
            this.insumo = insumo;
            preencheCampos(insumo);
        }

        private void preencheCampos(Insumo insumo) {
            nomeInsumo.setText(insumo.getNome());
            estoqueAtualInsumo.setText("Estoque Atual: " + Double.toString(insumo.getEstoqueAtual()) + " " + insumo.getUnidade());
        }
    }

    public void adiciona (Insumo insumo){
        insumos.add(insumo);
        notifyDataSetChanged();
    }

    public void atualiza(List<Insumo> insumos){
        this.insumos.clear();
        this.insumos.addAll(insumos);
        notifyDataSetChanged();
    }

    public void remove(Insumo insumo, int posicao){
        insumos.remove(insumo);
        notifyItemRemoved(posicao);
        notifyItemRangeChanged(posicao, insumos.size());
    }
}
