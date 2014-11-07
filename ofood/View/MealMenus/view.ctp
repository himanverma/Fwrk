<div class="mealMenus view">
<h2><?php echo __('Meal Menu'); ?></h2>
	<dl>
		<dt><?php echo __('Id'); ?></dt>
		<dd>
			<?php echo h($mealMenu['MealMenu']['id']); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Vendor Day'); ?></dt>
		<dd>
			<?php echo $this->Html->link($mealMenu['VendorDay']['id'], array('controller' => 'vendor_days', 'action' => 'view', $mealMenu['VendorDay']['id'])); ?>
			&nbsp;
		</dd>
		<dt><?php echo __('Recipe'); ?></dt>
		<dd>
			<?php echo $this->Html->link($mealMenu['Recipe']['recipe_name'], array('controller' => 'recipes', 'action' => 'view', $mealMenu['Recipe']['id'])); ?>
			&nbsp;
		</dd>
	</dl>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('Edit Meal Menu'), array('action' => 'edit', $mealMenu['MealMenu']['id'])); ?> </li>
		<li><?php echo $this->Form->postLink(__('Delete Meal Menu'), array('action' => 'delete', $mealMenu['MealMenu']['id']), array(), __('Are you sure you want to delete # %s?', $mealMenu['MealMenu']['id'])); ?> </li>
		<li><?php echo $this->Html->link(__('List Meal Menus'), array('action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Meal Menu'), array('action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Days'), array('controller' => 'vendor_days', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Day'), array('controller' => 'vendor_days', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Recipes'), array('controller' => 'recipes', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Recipe'), array('controller' => 'recipes', 'action' => 'add')); ?> </li>
	</ul>
</div>
