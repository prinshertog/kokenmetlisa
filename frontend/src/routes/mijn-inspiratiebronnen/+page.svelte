<script lang="ts">
  const { data } = $props();
</script>

<svelte:head>
  <title>Koken met Lisa | Mijn inspiraties</title>
</svelte:head>

<div class="container px-4 w-full">
  <div class="mb-8">
    <h1 class="text-3xl font-bold text-gray-900">Mijn inspiratiebronnen</h1>
    <p class="mt-2 text-gray-600">Bekijk hier de gerechten die mij hebben geïnspireerd.</p>
  </div>

  <div class="grid lg:grid-cols-4 md:grid-cols-3 sm:grid-cols-2 xs:grid-cols-1 gap-10">
    {#each data.pageObject.content as dish}
      <div class="overflow-hidden rounded-lg shadow-lg relative bg-white">
        <a aria-label="Link" href={dish.externalUrl ?? '#'} target="_blank" rel="noopener noreferrer" class="block">
          <div class="h-80 w-full bg-cover bg-center" style="background-image: url('{dish.imageUrl ?? '/placeholder.png'}');"></div>
          <div class="bg-[rgb(96,110,90)] p-4 text-white absolute bottom-0 w-full">
            {#if dish.categories?.length}
              <div class="flex flex-wrap gap-2 mb-3">
                {#each dish.categories as category}
                  <span class="text-[10px] font-semibold uppercase tracking-wide bg-blue-50 text-blue-700 px-2 py-1 rounded-full text-black">{category.name}</span>
                {/each}
              </div>
            {/if}
            <h2 class="text-lg font-bold">{dish.title}</h2>
            {#if dish.shortDescription}
              <p class="text-sm mt-1">{dish.shortDescription}</p>
            {/if}
            {#if dish.sourceName}
              <p class="mt-3 text-xs uppercase tracking-wide text-blue-100">Source: {dish.sourceName}</p>
            {/if}
          </div>
        </a>
      </div>
    {/each}
  </div>

  <div class="mt-8 flex justify-center gap-4">
    {#if data.pageObject.first == false}
      <a
        href={`?page=${data.pageObject.number - 1}`}
        class="rounded bg-gray-200 px-4 py-2 hover:bg-gray-300"
      >
        Vorige
      </a>
    {/if}

    <span class="px-4 py-2 font-semibold">Page {data.pageObject.number}</span>

    {#if data.pageObject.last == false}
      <a
        href={`?page=${data.pageObject.number + 1}`}
        class="rounded bg-gray-200 px-4 py-2 hover:bg-gray-300"
      >
        Volgende
      </a>
    {/if}
  </div>
</div>
