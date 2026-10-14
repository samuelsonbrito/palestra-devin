<script setup>
import { computed } from 'vue'

const props = defineProps({
  resposta: { type: Object, required: true },
})

function formatarData(iso) {
  if (!iso) return '—'
  const [ano, mes, dia] = iso.split('-')
  return `${dia}/${mes}/${ano}`
}

const vencimentoFormatado = computed(() => formatarData(props.resposta.vencimento))
const publicacaoFormatada = computed(() => formatarData(props.resposta.dataPublicacao))
</script>

<template>
  <section class="cartao" aria-live="polite">
    <dl class="detalhes">
      <div>
        <dt>Tipo de ato</dt>
        <dd>{{ resposta.tipoAto }}</dd>
      </div>
      <div>
        <dt>Publicação</dt>
        <dd>{{ publicacaoFormatada }}</dd>
      </div>
      <div>
        <dt>Prazo</dt>
        <dd>{{ resposta.diasUteis }} dias úteis</dd>
      </div>
    </dl>

    <div class="vencimento">
      <span class="rotulo">Vencimento</span>
      <span class="data">{{ vencimentoFormatado }}</span>
    </div>

    <p class="resumo">{{ resposta.resumo }}</p>
  </section>
</template>
