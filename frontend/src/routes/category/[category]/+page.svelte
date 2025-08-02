<script>
    export let data;
    const { categoryDishes } = data;
    import { env } from '$env/dynamic/public';
    const FILE_URL = env.PUBLIC_FILE_URL
</script>

<div class="container mx-auto px-4">
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        {#each categoryDishes as { id, name, imageName }}
            <div class="overflow-hidden rounded-lg shadow-lg">
                <a aria-label="Link" href="/dishes/{id}" class="block">
                    <div class="h-48 w-full bg-cover bg-center" 
                         style="background-image: url('{`${FILE_URL}/${imageName}`}');">
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