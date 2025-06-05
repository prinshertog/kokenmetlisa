<script lang="ts">
    const { data } = $props();
    const { dishes } = data;
</script>

<div class="container mx-auto px-4">
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        {#each dishes as { id, name, imageUrl }}
            <div class="overflow-hidden rounded-lg shadow-lg">
                <a aria-label="Link" href="/dishes/{id}" class="block">
                    <div class="h-48 w-full bg-cover bg-center" 
                         style="background-image: url('{imageUrl}');">
                    </div>
                    <div class="bg-[rgb(73,105,83)] p-4 text-white">
                        <h2 class="text-lg font-bold truncate">
                            {name}
                        </h2>
                    </div>
                </a>
            </div>
        {/each}
    </div>
</div>