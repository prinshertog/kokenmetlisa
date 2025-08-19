<script lang="ts">
    const { data } = $props();
    const { dishes } = data;
    import { env } from '$env/dynamic/public';
    const FILE_URL = env.PUBLIC_FILE_URL

    let reversedDishes = [...dishes].reverse();
</script>

<div class="container mx-auto px-4">
    <div class="flex gap-5">
        {#each reversedDishes as { id, name, imageName }}
            <div class="w-1/4 overflow-hidden rounded-lg shadow-lg relative">
                <a aria-label="Link" href="/dishes/{id}" class="block">
                    <div class="h-60 w-full bg-cover bg-center" 
                         style="background-image: url('{`${FILE_URL}/${imageName}`}');">
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