<div class="customers form">
<?php echo $this->Form->create('Customer',array('type'=>'file')); ?>
	<fieldset>
		<legend><?php echo __('Add Customer'); ?></legend>
	<?php
		echo $this->Form->input('deviceId');
		echo $this->Form->input('name');
		echo $this->Form->input('image',array('type'=>'file'));
		echo $this->Form->input('email');
		echo $this->Form->input('mobile_number');

	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit')); ?>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>

		<li><?php echo $this->Html->link(__('List Customers'), array('action' => 'index')); ?></li>
		<li><?php echo $this->Html->link(__('List Orders'), array('controller' => 'orders', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Order'), array('controller' => 'orders', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Reviews'), array('controller' => 'vendor_reviews', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Review'), array('controller' => 'vendor_reviews', 'action' => 'add')); ?> </li>
	</ul>
</div>
