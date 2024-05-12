<script setup lang="ts">
import {LabelValueVO} from '../../../api/BaseVoDef';
import {ref} from "vue";

interface Props {
  load: () => Promise<Array<LabelValueVO>>
}

const props = defineProps<Props>()

const emits = defineEmits(
    [
        "change"
    ]
)

const options = ref<Array<LabelValueVO>>([])


const init = async function () {
  options.value = await props.load()
}

init()
</script>

<template>

  <n-select :options="options" @update:value="(val, option) => {emits('change', option)}">
  </n-select>

</template>