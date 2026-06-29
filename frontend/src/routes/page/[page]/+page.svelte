<script lang="ts">
    const { data } = $props();
</script>

<div class="container w-full px-4">
    <div class="grid gap-10 xs:grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4">
        {#each [...data.pageObject.content].reverse() as { id, name, imageName }}
            <div class="relative overflow-hidden rounded-lg shadow-lg">
                <a href={`/dishes/${id}`} class="block" aria-label={name}>
                    <div
                        class="h-60 w-full bg-cover bg-center"
                        style={`background-image: url('/file/${imageName}')`}
                    ></div>

                    <div class="absolute bottom-0 w-full bg-[rgb(96,110,90)] p-4 text-white">
                        <h2 class="text-lg font-bold">
                            {name}
                        </h2>
                    </div>
                </a>
            </div>
        {/each}
    </div>
</div>

<div class="mt-8 flex justify-center gap-4">
    {#if data.pageObject.first == false}
    <a
        href={`/page/${data.pageObject.number - 1}`}
        class="rounded bg-gray-200 px-4 py-2 hover:bg-gray-300"
    >
        Vorige
    </a>
    {/if}

    <span class="px-4 py-2 font-semibold">
        Page {data.pageObject.number}
    </span>

    {#if data.pageObject.last == false}
        <a
            href={`/page/${data.pageObject.number + 1}`}
            class="rounded bg-gray-200 px-4 py-2 hover:bg-gray-300"
        >
            Volgende
        </a>
    {/if}
</div>