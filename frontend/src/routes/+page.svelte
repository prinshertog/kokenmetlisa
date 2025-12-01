<script lang="ts">
    const { data } = $props();
    const { dishes } = data;
    import { env } from '$env/dynamic/public';
    const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;

    let reversedDishes = [...dishes].reverse();
</script>

<div class="container px-4 w-full">
    <div class="grid lg:grid-cols-4 md:grid-cols-3 sm:grid-cols-2 xs:grid-cols-1 gap-10">
    {#each reversedDishes as { id, name, imageName }}
        <div class="overflow-hidden rounded-lg shadow-lg relative">
            <a aria-label="Link" href="/dishes/{id}" class="block">
                <div class="h-60 w-full bg-cover bg-center" 
                        style="background-image: url('{`${BASE_URL_BACKEND}/file/${imageName}`}');">
                </div>
                <div class="bg-[rgb(96,110,90)] p-4 text-white absolute bottom-0 w-full">
                    <h2 class="text-lg font-bold">
                        {name}
                    </h2>
                </div>
            </a>
        </div>
    {/each}
    </div>
</div>