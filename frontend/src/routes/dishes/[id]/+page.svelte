<script>
    export let data;
</script>

<svelte:head>
  <title>{data.name}</title>
</svelte:head>

<div class="flex">
    <div 
        class="h-screen w-1/2 bg-center bg-cover" 
        style="background-image: url({data.imageUrl});">
    </div>
    
    <div class="bg-[rgb(73,105,83)] p-4 text-white w-1/2">
        <h1 class="text-4xl">{data.name}</h1>
        <p class="pt-5">{data.description}</p>
    </div>
</div>