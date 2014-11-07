<div class="vendorReviews form">
<?php echo $this->Form->create('VendorReview'); ?>
	<fieldset>
		<legend><?php echo __('Add Vendor Review'); ?></legend>
	<?php
		echo $this->Form->input('customer_id');
		echo $this->Form->input('vendor_id');
		echo $this->Form->input('review');
		echo $this->Form->input('ratings');
	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit')); ?>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>

		<li><?php echo $this->Html->link(__('List Vendor Reviews'), array('action' => 'index')); ?></li>
		<li><?php echo $this->Html->link(__('List Customers'), array('controller' => 'customers', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Customer'), array('controller' => 'customers', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendors'), array('controller' => 'vendors', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor'), array('controller' => 'vendors', 'action' => 'add')); ?> </li>
	</ul>
</div>
