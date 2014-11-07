<div class="mealMenus form">
<?php echo $this->Form->create('MealMenu'); ?>
	<fieldset>
		<legend><?php echo __('Edit Meal Menu'); ?></legend>
	<?php
		echo $this->Form->input('id');
		echo $this->Form->input('vendor_day_id');
		echo $this->Form->input('recipe_id');
	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit')); ?>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>

		<li><?php echo $this->Form->postLink(__('Delete'), array('action' => 'delete', $this->Form->value('MealMenu.id')), array(), __('Are you sure you want to delete # %s?', $this->Form->value('MealMenu.id'))); ?></li>
		<li><?php echo $this->Html->link(__('List Meal Menus'), array('action' => 'index')); ?></li>
		<li><?php echo $this->Html->link(__('List Vendor Days'), array('controller' => 'vendor_days', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Day'), array('controller' => 'vendor_days', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Recipes'), array('controller' => 'recipes', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Recipe'), array('controller' => 'recipes', 'action' => 'add')); ?> </li>
	</ul>
</div>
