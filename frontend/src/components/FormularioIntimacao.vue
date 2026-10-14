<script setup>
import { ref } from 'vue'

defineProps({
  carregando: { type: Boolean, default: false },
})

const emit = defineEmits(['calcular'])

const intimacao = ref(
  'Fica a parte ré, FULANO DE TAL, intimada para apresentar contestação no prazo ' +
    'de 15 (quinze) dias, nos autos do processo nº 0000000-00.2026.8.04.0001. ' +
    'Data da publicação: 16/10/2026.'
)
</script>

<template>
  <form class="formulario" @submit.prevent="emit('calcular', intimacao)">
    <label for="intimacao">Texto da intimação</label>
    <textarea
      id="intimacao"
      v-model="intimacao"
      rows="8"
      placeholder="Cole aqui o texto da intimação judicial..."
    ></textarea>
    <button type="submit" :disabled="carregando || !intimacao.trim()">
      {{ carregando ? 'Analisando a intimação...' : 'Calcular prazo' }}
    </button>
  </form>
</template>
