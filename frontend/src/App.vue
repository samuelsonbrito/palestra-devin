<script setup>
import { ref } from 'vue'
import FormularioIntimacao from './components/FormularioIntimacao.vue'
import CartaoPrazo from './components/CartaoPrazo.vue'

const carregando = ref(false)
const resultado = ref(null)
const erro = ref(null)

async function calcular(intimacao) {
  carregando.value = true
  resultado.value = null
  erro.value = null
  try {
    const resposta = await fetch('/api/prazos', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ intimacao }),
    })
    const corpo = await resposta.json().catch(() => null)
    if (!resposta.ok) {
      erro.value = corpo?.erro ?? 'Não foi possível analisar a intimação.'
      return
    }
    resultado.value = corpo
  } catch {
    erro.value = 'Não foi possível falar com o servidor. Verifique se o back-end está rodando.'
  } finally {
    carregando.value = false
  }
}
</script>

<template>
  <div class="pagina">
    <main class="conteudo">
      <header>
        <h1>Assistente de Prazos</h1>
        <p class="subtitulo">
          Cole o texto de uma intimação judicial e a IA calcula a data de
          vencimento do prazo em dias úteis.
        </p>
      </header>

      <FormularioIntimacao :carregando="carregando" @calcular="calcular" />

      <p v-if="carregando" class="status">Analisando a intimação...</p>

      <CartaoPrazo v-if="resultado" :resposta="resultado" />

      <div v-else-if="erro" class="cartao-erro" role="alert">
        <strong>Algo deu errado.</strong>
        <p>{{ erro }}</p>
      </div>
    </main>

    <footer class="rodape">
      Exemplo didático. Não usar para prazos reais.
    </footer>
  </div>
</template>
